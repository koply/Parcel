package com.karpuzdev.parcel.lang.expressions;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
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
    public CompileResult compile(CompileInformation info, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        bytes.addAll(ByteUtil.split(TileBytes.COMMAND_EVENT));

        // Line number parameter
        bytes.addAll(ByteUtil.splitTrim(info.lineNumber));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Block end specifier
        // Will come back and set this in the compiler
        int blockEndSpecifierPosition = bytes.size();
        bytes.add(TileBytes.NULL_TERMINATOR);

        // TODO: Sanitize String parameters
        // Command text parameter
        bytes.addAll(ByteUtil.split(groups[0]));
        bytes.add(TileBytes.NULL_TERMINATOR);

        List<Byte> trailerBytes = new Vector<>(10, 10);

        trailerBytes.addAll(ByteUtil.split(TileBytes.RETURN_ACTION));

        return new CompileResult(blockEndSpecifierPosition, bytes, trailerBytes);
    }
}