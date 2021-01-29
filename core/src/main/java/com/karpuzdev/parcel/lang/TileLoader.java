package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class TileLoader {

    private static final Map<File, List<Byte>> contentCache = new HashMap<>();

    // TODO: Linking
    static void loadAll(File[] files) {
        for (File file : files) {
            load(file);
        }
    }

    static void load(File file) {
        if (file.isDirectory() || file.isHidden()) throw new IllegalArgumentException("Tiles have to be non-hidden files.");

        // files should be like "hello.tile" (with .tile extension)
        if (!FileUtil.getFileExtension(file).equals("tile")) throw new IllegalArgumentException("Tiles have to have .tile extension.");

        byte[] content = FileUtil.readFileBytes(file);

        int header = (content[0] << 16) | (content[1] << 8) | (content[2]);
        if (header != TileBytes.TILE_HEADER) throw new IllegalArgumentException("Invalid Tile (Invalid Header)");

        // Currently unused
        int _version = content[3];

        List<Byte> bytes = new ArrayList<>(content.length);

        for (byte b : content) {
            bytes.add(b);
        }

        contentCache.put(file, bytes);

        TileScanner.scanFile(file);
    }

    static List<Byte> read(File file) {
        return contentCache.get(file);
    }

}