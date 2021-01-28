package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

final class ParcelCompiler {

    private ParcelCompiler() { }

    static void compileAll(File[] parcels, File outputFolder) {

        // we don't want duplicate names
        Set<String> names = new HashSet<>();

        for (File parcel : parcels) {
            if (parcel.isDirectory() || parcel.isHidden()) continue;

            // files should be like "hello.parcel" (with .parcel extension)
            if (!FileUtil.getFileExtension(parcel).equals("parcel")) continue;

            if (names.contains(parcel.getName())) throw new IllegalArgumentException(parcel.getName() + " is duplicated. There cannot be duplicate names.");
            names.add(parcel.getName());

            compileFile(parcel, outputFolder);
        }
    }

    static void compileFile(File parcel, File outputFolder) {
        if (parcel.isDirectory() || parcel.isHidden()) throw new IllegalArgumentException("Parcels have to be non-hidden files.");

        // files should be like "hello.parcel" (with .parcel extension)
        if (!FileUtil.getFileExtension(parcel).equals("parcel")) throw new IllegalArgumentException("Parcels have to have .parcel extension.");

        String code = FileUtil.readFile(parcel);
        compileCode(code, parcel.getName().substring(0, parcel.getName().lastIndexOf('.')), outputFolder);
    }

    static void compileCode(String code, String name, File outputFolder) {
        List<Byte> bytes = new Vector<>(10, 10);



        try {
            File tile = new File(outputFolder, name + ".tile");
            tile.createNewFile();

            FileUtil.writeFileBytes(tile, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}