package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.exceptions.TileException;
import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;
import com.karpuzdev.parcel.lang.helpers.ExpressionPosition;
import com.karpuzdev.parcel.lang.helpers.ParsedParameter;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

/**
 * Executes tile bytes
 */
public abstract class ByteExecutor {

    public abstract short getIdentifier();
    public abstract ExecutionResult execute(ExecutionInfo info);

    protected static ParsedParameter parseParameter(ExpressionPosition expPos, List<Byte> bytes, int position, byte identifier) {

        // TODO: Property matcher
        if (identifier == TileBytes.PROPERTY_IDENTIFIER) {
            return null;
        }

        List<Byte> buffer = ByteUtil.bufferUntilNull(bytes, position);

        if (identifier == TileBytes.STRING_IDENTIFIER) {
            String text = ByteUtil.packStringBytes(buffer);

            return new ParsedParameter(position + buffer.size() + 1, text);

        } else if (identifier == TileBytes.NUMBER_IDENTIFIER) {
            long num = ByteUtil.packNumberBytes(buffer);

            return new ParsedParameter(position + buffer.size() + 1, num);

        } else if (identifier == TileBytes.DECIMAL_IDENTIFIER) {
            double num = ByteUtil.packDecimalBytes(buffer);

            return new ParsedParameter(position + buffer.size() + 1, num);

        } else {
            throw new TileException(expPos, "Could not understand parameter");
        }

    }

}