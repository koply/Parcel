package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class SetCounterExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.SETCOUNTER_ACTION;
    }

    @Override
    public ExecutionResult execute(ExecutionInfo info) {
        info.state.clearElseFlag();

        int pos = info.position + 2;

        // Line number parameter
        List<Byte> lineBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long _lineNumber = ByteUtil.packNumberBytes(lineBytes);

        pos += lineBytes.size() + 1;

        // Counter set parameter
        List<Byte> counterBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long counter = ByteUtil.packNumberBytes(counterBytes);

        pos += counterBytes.size() + 1;

        // Execution
        info.state.setCounter(counter);

        return new ExecutionResult(pos);
    }
}