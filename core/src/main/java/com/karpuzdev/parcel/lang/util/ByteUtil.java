package com.karpuzdev.parcel.lang.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteUtil {

    public static List<Byte> split(String arg) {
        List<Byte> list = new ArrayList<>();

        for (byte b : arg.getBytes(StandardCharsets.UTF_8)) {
            list.add(b);
        }

        return list;
    }

    public static List<Byte> split(long arg) {
        List<Byte> list = new ArrayList<>(8);
        list.add((byte) (arg >> 56));
        list.add((byte) (arg >> 48));
        list.add((byte) (arg >> 40));
        list.add((byte) (arg >> 32));
        list.addAll(split((int) arg));

        return list;
    }

    public static List<Byte> split(int arg) {
        List<Byte> list = new ArrayList<>(4);
        list.add((byte) (arg >> 24));
        list.add((byte) (arg >> 16));
        list.addAll(split((short) arg));

        return list;
    }

    public static List<Byte> split(short arg) {
        List<Byte> list = new ArrayList<>(4);
        list.add((byte) (arg >> 8));
        list.add((byte) arg);

        return list;
    }

    public static List<Byte> splitTrim(long arg) {
        return trimBytes(split(arg));
    }

    public static List<Byte> splitTrim(int arg) {
        return trimBytes(split(arg));
    }

    public static List<Byte> splitTrim(short arg) {
        return trimBytes(split(arg));
    }

    // You know... I fucking hate the "every number is signed" bullshit in Java
    public static String toPrettyString(List<Byte> list) {
        StringBuilder strBuilder = new StringBuilder();

        boolean start = true;
        for (byte b : list) {
            if (!start) strBuilder.append(" ");
            else start = false;

            String hex = Integer.toHexString(Byte.toUnsignedInt(b));
            if (hex.length() == 1) hex = "0" + hex;

            strBuilder.append(hex);
        }

        return strBuilder.toString();
    }

    public static List<Byte> trimBytes(List<Byte> bytes) {
        List<Byte> trimmed = new ArrayList<>(bytes.size());

        boolean trimming = true;
        for (byte b : bytes) {
            if (b != 0) trimming = false;
            if (trimming) continue;

            trimmed.add(b);
        }

        return trimmed;
    }

}