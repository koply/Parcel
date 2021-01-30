package com.karpuzdev.parcel.lang.tiles.scanners;

import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.helpers.EventInformation;
import com.karpuzdev.parcel.lang.helpers.ScanInformation;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class OnCommandScanner extends ByteScanner {

    @Override
    public short getIdentifier() {
        return TileBytes.COMMAND_EVENT;
    }

    @Override
    public EventInformation scan(ScanInformation info) {
        // First 2 bytes are event identifier bytes so we don't care
        int pos = info.position + 2;

        // Line number parameter. Currently unused
        List<Byte> lineBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long _lineNumber = ByteUtil.packNumberBytes(lineBytes);

        // +1 because of the null byte
        pos += lineBytes.size()+1;

        // Block end parameter
        List<Byte> blockEndBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long blockEnd = ByteUtil.packNumberBytes(blockEndBytes);

        pos += blockEndBytes.size()+1;

        // Command text parameter
        List<Byte> textBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        String text = ByteUtil.packStringBytes(textBytes);

        pos += textBytes.size()+1;

        // End
        EventIdentifier identifier = new EventIdentifier(TileBytes.COMMAND_EVENT, text);
        EventInformation eventInfo = new EventInformation(identifier, pos, (int) blockEnd);

        return eventInfo;
    }
}