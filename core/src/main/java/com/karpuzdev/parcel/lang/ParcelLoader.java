package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.util.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

final class ParcelLoader {

    private ParcelLoader() { }

    static void load(File[] parcels) {
        for (File parcel : parcels) {
            if (parcel.isDirectory() || parcel.isHidden()) continue;

            // files should be like "hello.parcel" (with .parcel extension)
            if (!FileUtil.getFileExtension(parcel).equals("parcel")) continue;

            String code = FileUtil.readFile(parcel);
        }
    }

}