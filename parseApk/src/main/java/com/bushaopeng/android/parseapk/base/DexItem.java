package com.bushaopeng.android.parseapk.base;

/**
 * Created by bushaopeng on 17/3/16.
 */
public class DexItem {
    public static final int TYPE_INT_SIZE = 1;
    public static final int TYPE_INT_OFFSET = 2;
    public static final int TYPE_INT_INFO = 3;
    public String name;
    public int type;
    public int start = -1;
    public int byteSize = -1;
    public byte[] data;
    public String desc;

    public int bytes2Int() {
        int mask = 0xff;
        if (byteSize == 4) {
            int temp = 0;
            int n = 0;
            temp = data[3] & mask;
            n |= temp;
            n = n << 8;
            temp = data[2] & mask;
            n |= temp;
            n = n << 8;
            temp = data[1] & mask;
            n |= temp;
            n = n << 8;
            temp = data[0] & mask;
            n |= temp;
            return n;
        }
        return -1;
    }

}
