package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.*;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

public final class EqualsExecutor extends ByteExecutor {

    @Override
    public short getIdentifier() {
        return TileBytes.EQUALS_CONDITION;
    }

    @Override
    public ExecutionResult execute(ExecutionInfo info) {
        info.state.clearElseFlag();

        int pos = info.position + 2;

        // Line number parameter
        List<Byte> lineBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long lineNumber = ByteUtil.packNumberBytes(lineBytes);

        pos += lineBytes.size()+1;

        // Block end parameter
        List<Byte> blockEndBytes = ByteUtil.bufferUntilNull(info.bytes, pos);
        long blockEnd = ByteUtil.packNumberBytes(blockEndBytes);

        pos += blockEndBytes.size()+1;

        ExpressionPosition expPos = new ExpressionPosition(info.fileName, lineNumber);

        // First parameter
        byte param1Id = info.bytes.get(pos);
        pos += 1;

        ParsedParameter param1 = ByteExecutor.parseParameter(expPos, info.bytes, pos, param1Id);
        pos = param1.newPosition;

        // Second parameter
        byte param2Id = info.bytes.get(pos);
        pos += 1;

        ParsedParameter param2 = ByteExecutor.parseParameter(expPos, info.bytes, pos, param2Id);
        pos = param2.newPosition;


        // Checking

        // Check fails
        if (param1.type != param2.type) {
            info.state.setElseFlag();
            return new ExecutionResult((int)blockEnd);
        }

        // Check fails
        if (param1.type == ParameterType.STRING) {
            if (!param1.stringParam.equals(param2.stringParam)) {
                info.state.setElseFlag();
                return new ExecutionResult((int)blockEnd);
            }
        }

        // Check fails
        if (param1.type == ParameterType.NUMBER) {
            if (param1.numberParam != param2.numberParam) {
                info.state.setElseFlag();
                return new ExecutionResult((int)blockEnd);
            }
        }

        // Check fails
        if (param1.type == ParameterType.DECIMAL) {
            if (param1.decimalParam != param2.decimalParam) {
                info.state.setElseFlag();
                return new ExecutionResult((int)blockEnd);
            }
        }

        return new ExecutionResult(pos);
    }
}