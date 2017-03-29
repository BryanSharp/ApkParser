package com.bushaopeng.android.parseapk;


import com.bushaopeng.android.parseapk.base.DexData;
import com.bushaopeng.android.parseapk.base.DexDataItem;
import com.bushaopeng.android.parseapk.data.ClassContent;
import com.bushaopeng.android.parseapk.refs.ClassRef;
import com.bushaopeng.android.parseapk.utils.LogUtils;
import com.bushaopeng.android.parseapk.utils.Mutf8;
import com.bushaopeng.android.parseapk.utils.Utils;

import java.util.Map;

/**
 * Created by bsp on 17/3/18.
 */
public class ClassDefsItem extends DexDataItem<ClassRef, ClassContent> {
    public ClassDefsItem(String name) {
        super(name);
    }

    @Override
    protected ClassRef[] createRefs() {
        return new ClassRef[count];
    }

    @Override
    protected int getRefSize() {
        return ClassRef.SIZE;
    }

    @Override
    protected ClassRef parseRef(int i) {
        ClassRef classRef = new ClassRef();
        classRef.classIdx = Utils.bytesToInt(data, i);
        i += 4;
        classRef.accessFlags = Utils.bytesToInt(data, i);
        i += 4;
        classRef.superclassIdx = Utils.bytesToInt(data, i);
        i += 4;
        classRef.interfacesOff = Utils.bytesToInt(data, i);
        i += 4;
        classRef.sourceFileIdx = Utils.bytesToInt(data, i);
        i += 4;
        classRef.annotationsOff = Utils.bytesToInt(data, i);
        i += 4;
        classRef.classDataOff = Utils.bytesToInt(data, i);
        i += 4;
        classRef.staticValuesOff = Utils.bytesToInt(data, i);
        return classRef;
    }

    @Override
    public void parse2ndRealData(Map<String, DexDataItem> dataItems, byte[] dexData) {
        TypeIdsItem item = (TypeIdsItem) dataItems.get(DexData.TYPE_IDS);
        StringIdsItem sItem = (StringIdsItem) dataItems.get(DexData.STRING_IDS);
        String[] realData = item.realData;
        this.realData = new ClassContent[refs.length];

        for (int i = 0; i < refs.length; i++) {
            ClassRef ref = refs[i];
            ClassContent classContent = new ClassContent();
            classContent.className = realData[ref.classIdx];
            classContent.superClassName = realData[ref.superclassIdx];
            classContent.sourceFile = sItem.realData[ref.sourceFileIdx];
            int classDataOff = ref.classDataOff;
            int[] sizes = new int[4];
            int sizeOfSizes = 0;
            for (int j = 0; j < 4; j++) {
                int[] result = Mutf8.readUnsignedLeb128(dexData, classDataOff);
                sizes[j] = result[0];
                classDataOff += result[1];
                sizeOfSizes += result[1];
            }
            classContent.classData = new ClassContent.ClassData(sizes);
            classContent.classData.sizeOfSizes = sizeOfSizes;
            if (i < refs.length - 1) {
                int size = refs[i + 1].classDataOff - ref.classDataOff;
                classContent.classData.data = new byte[size];
                System.arraycopy(dexData, ref.classDataOff, classContent.classData.data, 0, size);
            } else {
                int size = dexData.length - ref.classDataOff;
                classContent.classData.data = new byte[size];
                System.arraycopy(dexData, ref.classDataOff, classContent.classData.data, 0, size);
            }
            this.realData[i] = classContent;
        }
    }

    @Override
    public void parse4thRealData(Map<String, DexDataItem> dataItems, byte[] dexData) {
        for (int i = 0; i < realData.length; i++) {
            ClassContent content = realData[i];
            LogUtils.log("current parsing " + i);
            content.classData.fillData(dataItems, dexData);
        }
    }
}
