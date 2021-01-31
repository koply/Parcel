package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class ElseExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.ELSE_CONDITION;
    }

    @Override
    public ExecutionResult execute(ExecutionInfo info) {

        int pos = info.position+2;

        // Line number parameter
        List<Byte> lineBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long lineNumber = ByteUtil.packNumberBytes(lineBytes);

        pos += lineBytes.size()+1;

        // Block end parameter
        List<Byte> blockEndBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long blockEnd = ByteUtil.packNumberBytes(blockEndBytes);

        pos += blockEndBytes.size()+1;

        // Check fails
        if (!info.state.checkElseFlag()) {
            return new ExecutionResult((int)blockEnd);
        }

        info.state.clearElseFlag();
        return new ExecutionResult(pos);
    }
}