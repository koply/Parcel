package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class JumpCounterExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.JUMPCOUNTER_CONDITION;
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
        List<Byte> loopStartBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long loopStart = ByteUtil.packNumberBytes(loopStartBytes);

        pos += loopStartBytes.size() + 1;

        if (!info.state.checkCounterZero()) {
            info.state.decrementCounter();
            return new ExecutionResult((int)loopStart);
        }

        return new ExecutionResult(pos);
    }
}