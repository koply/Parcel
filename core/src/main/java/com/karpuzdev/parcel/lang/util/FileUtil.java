package com.karpuzdev.parcel.lang.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class FileUtil {

    public static String getFileExtension(File file) {
        String name = file.getName();
        int pointIndex = name.lastIndexOf('.');

        if (pointIndex == name.length()-1) return "";
        return name.substring(pointIndex+1);
    }

    public static String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            StringBuilder code = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line);
            }

            return code.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readFileBytes(File file) {
        try {
            FileInputStream input = new FileInputStream(file);

            byte[] content = new byte[(int)file.length()];
            int _bytesRead = input.read(content);

            return content;

        } catch (IOException e) {
            e.printStackTrace();

            return new byte[0];
        }

    }

}