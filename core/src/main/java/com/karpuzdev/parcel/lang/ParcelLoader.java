package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;

final class ParcelLoader {

    private ParcelLoader() { }

    static void load(File[] parcels) {
        for (File parcel : parcels) {
            if (parcel.isDirectory() || parcel.isHidden()) continue;

            // files should be like "hello.parcel" (with .parcel extension)
            if (!Util.getFileExtension(parcel).equals("parcel")) continue;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(parcel), StandardCharsets.UTF_8))) {

                StringBuilder code = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    code.append(line);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}