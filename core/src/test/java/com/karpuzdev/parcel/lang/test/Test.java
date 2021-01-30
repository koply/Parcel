package com.karpuzdev.parcel.lang.test;

import com.karpuzdev.parcel.lang.ParcelAPI;

import java.io.File;

public class Test {

    public static void main(String[] args) {
//        String code = "on command \"ping\"\n" +
//                "\tif channel.name is \"botkomut\"\n" +
//                "\t\trespond with text \"Pong!\"\n" +
//                "\telse:\n" +
//                "\t\tsend text \"Got a Message!\" to channel \"botkomut\"";
        String code =
                    "on command \"ping\"\n" +
                    "\trespond with text \"Pong!\"\n\n" +
                    "\trespond with text \"Pongo!\"\n" +
                    "on command \"help\"\n\n" +
                    "\trespond with text \"Help!\"\n";

        File outputFolder = new File("D:\\tmp\\tiletest");
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
//        System.out.println(ParcelAPI.compileCodeToFile(code, outputFolder));
//        ParcelAPI.compileCodeToFile(code, outputFolder);

        File tile = new File(outputFolder, "test.tile");
        ParcelAPI.loadTile(tile);

        long last = System.currentTimeMillis();

//        for (int i = 0; i < 47500; i++) {
//            ParcelAPI.executeEvent(new EventIdentifier(TileBytes.COMMAND_EVENT, "ping"));
//        }
        for (int i = 0; i < 35000; i++) {
            ParcelAPI.compileCodeToString(code);
        }

        System.out.println("\nTime: " + ((double) System.currentTimeMillis() - last) + " ms");
    }

}