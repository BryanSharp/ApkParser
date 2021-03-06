package com.bushaopeng.android.parseapk.base;


import com.bushaopeng.android.parseapk.utils.Utils;

/**
 * Created by bsp on 17/3/19.
 */
public class DvmOpcode {
    public String form;
    public String formForUse;
    public int opcode;
    public int opSize;
    public String pattern;
    public String desc;

    public DvmOpcode(int opcode, String pattern, String desc) {
        this.opcode = opcode;
        this.pattern = pattern.trim();
        this.desc = desc.trim();
        char c = pattern.charAt(0);
        if (c != 'r') {
            opSize = Integer.parseInt(c + "");
        }
        this.form = Utils.getPatternFormMap().get(pattern);
        if (this.form != null) {
            this.formForUse = form.replaceAll("[|\\s]+", "").replaceAll("op.", "op").replace("lo", "").replace("hi", "");
        }
    }

    public DvmOpcode(int opcode, String pattern, String desc, String form) {
        this(opcode, pattern, desc);
        this.form = form;
    }

    @Override
    public String toString() {
        return "\n\t\t\t\tDvmOpcode{" +
                "opcode=" + opcode +
                ", opSize=" + opSize +
                ", pattern='" + pattern + '\'' +
                ", desc='" + desc + '\'' +
                ", form='" + form + '\'' +
                ", formForUse='" + formForUse + '\'' +
                '}';
    }
}
