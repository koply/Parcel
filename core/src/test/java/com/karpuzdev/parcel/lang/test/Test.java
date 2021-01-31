package com.karpuzdev.parcel.lang.test;

import com.karpuzdev.parcel.lang.ParcelAPI;
import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.tiles.TileBytes;

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
                    "\tif \"test\" equals \"test\"\n" +
                    "\t\tif 5.3 is 5.4\n" +
                    "\t\t\trespond with text \"We are in if:if\"\n" +
                    "\t\totherwise\n" +
                    "\t\t\trespond with text \"Now in if:otherwise\"\n" +
                    "\telse\n" +
                    "\t\trespond with text \"Pong!\"\n" +
                    "on command \"help\"\n" +
                    "\trespond with text \"Help!\"\n";
//        String code =
//                    "on command \"ping\"\n" +
//                    "\tif \"tes\" equals \"test\"\n" +
//                    "\t\trespond with text \"Pongo!\"\n" +
//                    "\telse\n" +
//                    "\t\trespond with text \"Pong!\"\n\n" +
//                    "on command \"help\"\n" +
//                    "\trespond with text \"Help!\"\n";

        System.out.println(code);
        System.out.println();

        File outputFolder = new File("D:\\tmp\\tiletest");
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
//        System.out.println(ParcelAPI.compileCodeToString("if \"test\" equals \"test\""));
        ParcelAPI.compileCodeToFile(code, outputFolder);

        File tile = new File(outputFolder, "test.tile");
        ParcelAPI.loadTile(tile);
        ParcelAPI.executeEvent(new EventIdentifier(TileBytes.COMMAND_EVENT, "ping"));

//        long last = System.currentTimeMillis();

//        for (int i = 0; i < 47500; i++) {
//            ParcelAPI.executeEvent(new EventIdentifier(TileBytes.COMMAND_EVENT, "ping"));
//        }
//        for (int i = 0; i < 15000; i++) {
//            ParcelAPI.compileCodeToString(code);
//        }
//
//        System.out.println("\nTime: " + ((double) System.currentTimeMillis() - last) + " ms");
    }

}