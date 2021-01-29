package com.karpuzdev.parcel.lang.util;

import com.karpuzdev.parcel.lang.tiles.TileBytes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    public static long packNumberBytes(List<Byte> bytes) {
        long packed = 0;

        for (byte b : bytes) {
            packed = (packed << 8) | b;
        }

        return packed;
    }

    public static BigInteger packBigNumberBytes(List<Byte> bytes) {
        byte[] arr = new byte[bytes.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = bytes.get(i);
        }

        return new BigInteger(arr);
    }

    public static String packStringBytes(List<Byte> bytes) {
        byte[] arr = new byte[bytes.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = bytes.get(i);
        }

        return new String(arr);
    }

    public static short packIdentifierBytes(List<Byte> bytes, int pos) {
        byte b1 = bytes.get(pos);
        byte b2 = bytes.get(pos+1);

        return (short) ((b1 << 8) | b2);
    }

    public static long packNumberBytes(List<Byte> bytes, int pos) {
        return packNumberBytes(bufferUntilNull(bytes, pos));
    }

    public static BigInteger packBigNumberBytes(List<Byte> bytes, int pos) {
        return packBigNumberBytes(bufferUntilNull(bytes, pos));
    }

    public static String packStringBytes(List<Byte> bytes, int pos) {
        return packStringBytes(bufferUntilNull(bytes, pos));
    }

    public static List<Byte> bufferUntilNull(List<Byte> bytes, int pos) {
        List<Byte> buffer = new Vector<>(10, 10);

        while (bytes.get(pos) != TileBytes.NULL_TERMINATOR) {
            buffer.add(bytes.get(pos));
            pos += 1;
        }

        return buffer;
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

    /**
     * Trims unused(zero) leading bytes
     *
     * @param bytes the byte list
     * @return trimmed byte list
     */

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