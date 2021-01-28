package com.karpuzdev.parcel.lang.util;

import java.io.File;

public final class Util {

    public static String getFileExtension(File file) {
        String name = file.getName();
        int pointIndex = name.lastIndexOf('.');

        if (pointIndex == name.length()-1) return "";
        return name.substring(pointIndex+1);
    }

}