package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;

public final class ReturnExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.RETURN_ACTION;
    }

    @Override
    public ExecutionResult execute(ExecutionInfo info) {

        // This action only stops the current code execution

        return new ExecutionResult(true);
    }
}