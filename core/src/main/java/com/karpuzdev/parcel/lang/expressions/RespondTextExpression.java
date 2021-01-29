package com.karpuzdev.parcel.lang.expressions;

import com.karpuzdev.parcel.lang.expressions.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class RespondTextExpression extends TileExpression {

    @Override
    public List<String> getMatchers() {
        return Arrays.asList("^respond with (?:text )?\"(.*?)\"$");
    }

    @Override
    public CompileResult compile(String line, int lineNumber, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        bytes.addAll(ByteUtil.split(TileBytes.RESPONDTEXT_ACTION));

        // Line number parameter
        bytes.addAll(ByteUtil.splitTrim(lineNumber));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Message text parameter
        bytes.addAll(ByteUtil.split(groups[0]));
        bytes.add(TileBytes.NULL_TERMINATOR);

        return new CompileResult(bytes);
    }
}