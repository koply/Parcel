package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.exceptions.TileException;
import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.tiles.ExecutionState;
import com.karpuzdev.parcel.lang.tiles.executors.ByteExecutor;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class TileExecutor {

    private static final Map<Short, ByteExecutor> executorMap = new HashMap<>();

    static void registerExecutor(ByteExecutor executor) {
        executorMap.put(executor.getIdentifier(), executor);
    }

    static void execute(EventIdentifier identifier) {
        // Events are stored in the scanner
        TileScanner.execute(identifier);
    }

    static void execute(String fileName, List<Byte> bytes, int position) {

        ExecutionState state = new ExecutionState();

        boolean shouldStop = false;

        while (!shouldStop) {

            short identifier = ByteUtil.packIdentifierBytes(bytes, position);
            ByteExecutor executor = executorMap.get(identifier);

            if (executor == null) throw new TileException(fileName, "Invalid expression bytes");

            ExecutionInfo info = new ExecutionInfo(fileName, bytes, position, state);
            ExecutionResult result = executor.execute(info);

            position = result.expressionEnd;
            shouldStop = result.shouldStop;

        }

    }

}