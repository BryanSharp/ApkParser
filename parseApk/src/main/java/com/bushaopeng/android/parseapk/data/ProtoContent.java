package com.bushaopeng.android.parseapk.data;

/**
 * Created by bsp on 17/3/18.
 */
public class ProtoContent {
    public String shorty;
    public String returnType;
    public String parameters;

    @Override
    public String toString() {
        return "\nProtoContent{" +
                "shorty='" + shorty + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
