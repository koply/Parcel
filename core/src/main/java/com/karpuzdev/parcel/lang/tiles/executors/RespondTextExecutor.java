package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class RespondTextExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.RESPONDTEXT_ACTION;
    }

    @Override
    public ExecutionResult execute(ExecutionInfo info) {
        info.state.clearElseFlag();

        // First 2 bytes are identifier bytes
        int pos = info.position + 2;

        // Line number parameter - Currently unused
        List<Byte> lineBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long _lineNumber = ByteUtil.packNumberBytes(lineBytes);

        // +1 because of the null byte
        pos += lineBytes.size()+1;

        // Message text parameter
        List<Byte> textBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        String text = ByteUtil.packStringBytes(textBytes);

        pos += textBytes.size()+1;

        // Execution
        // TODO: Actual respond with text action
        System.out.println("Responding Text: " + text);

        // End
        ExecutionResult result = new ExecutionResult(pos);

        return result;
    }
}