package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.exceptions.ScanException;
import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.helpers.EventInformation;
import com.karpuzdev.parcel.lang.helpers.EventPosition;
import com.karpuzdev.parcel.lang.helpers.ScanInformation;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.tiles.scanners.ByteScanner;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General tile scanning manager
 */
final class TileScanner {

    private static final Map<EventIdentifier, List<EventPosition>> eventMap = new HashMap<>();
    private static final Map<Short, ByteScanner> scannerMap = new HashMap<>();

    static void registerScanner(ByteScanner scanner) {
        scannerMap.put(scanner.getIdentifier(), scanner);
    }

    static void scanFile(File file) {
        List<Byte> bytes = TileLoader.read(file);
        if (bytes == null) {
            // Tile might be unloaded
            // TODO: Maybe throw an exception?
            return;
        }

        // First 4 bytes are the header
        int pos = 4;

        while (pos < bytes.size()) {
            short identifier = ByteUtil.packIdentifierBytes(bytes, pos);
            ByteScanner scanner = scannerMap.get(identifier);

            if (scanner == null) throw new ScanException(file.getName(), "Invalid identifier bytes");

            ScanInformation scanInfo = new ScanInformation(bytes, pos);
            EventInformation eventInfo = scanner.scan(scanInfo);

            EventPosition eventPos = new EventPosition(file, eventInfo.blockStart);

            List<EventPosition> positions = eventMap.get(eventInfo.identifier);
            if (positions == null) {
                positions = new ArrayList<>();
            }

            positions.add(eventPos);
            eventMap.put(eventInfo.identifier, positions);

            pos = eventInfo.blockEnd;
        }
    }

    static boolean execute(EventIdentifier identifier) {
        List<EventPosition> positions = eventMap.get(identifier);

        if (positions == null) return false;

        for (EventPosition pos : positions) {
            List<Byte> bytes = TileLoader.read(pos.file);
            if (bytes == null) {
                // Tile might be unloaded
                continue;
            }

            TileExecutor.execute(pos.file.getName(), bytes, pos.position);
        }

        return true;
    }

    static void debug() {
        for (var entry : eventMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        EventIdentifier identifier = new EventIdentifier(TileBytes.COMMAND_EVENT, "ping");
        System.out.println(eventMap.get(identifier));
    }

}