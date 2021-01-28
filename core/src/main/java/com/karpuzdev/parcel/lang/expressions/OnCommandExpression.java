package com.karpuzdev.parcel.lang.expressions;

import com.karpuzdev.parcel.lang.expressions.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class OnCommandExpression extends TileExpression {

    @Override
    public List<String> getMatchers() {
        return Arrays.asList("^on command \"(.*?)\"$");
    }

    @Override
    public CompileResult compile(String line, int lineNumber, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        bytes.addAll(ByteUtil.split(TileBytes.COMMAND_EVENT));

        // Line number parameter
        bytes.addAll(ByteUtil.split(lineNumber));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Block end specifier
        // Will come back and set this in the compiler
        int blockEndSpecifierPosition = bytes.size();
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Command text parameter
        bytes.addAll(ByteUtil.split(groups[0]));
        bytes.add(TileBytes.NULL_TERMINATOR);

        return new CompileResult(blockEndSpecifierPosition, bytes);
    }
}