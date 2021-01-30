package com.karpuzdev.parcel.lang.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/*
TODO: Might need to add multiple byte-buffer support
 */
public final class FileUtil {

    private FileUtil() { }

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
        try (FileInputStream input = new FileInputStream(file)) {

            byte[] content = new byte[(int) file.length()];
            int _bytesRead = input.read(content);

            return content;

        } catch (IOException e) {
            e.printStackTrace();

            return new byte[0];
        }
    }

    public static void writeFile(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write(content);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileBytes(File file, byte[] bytes) {
        try (FileOutputStream output = new FileOutputStream(file)) {

            output.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileBytes(File file, List<Byte> bytes) {
        byte[] arr = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            arr[i] = bytes.get(i);
        }

        writeFileBytes(file, arr);
    }

}