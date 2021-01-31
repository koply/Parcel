package com.karpuzdev.parcel.lang.compilers;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class EqualsCompiler extends TileCompiler {

    @Override
    public List<String> getMatchers() {
        return Arrays.asList("^if ([^\\s\"]*?) (?:is|equals) ([^\\s\"]*?)$",
                "^if (\".*?\") (?:is|equals) (\".*?\")");
    }

    @Override
    public CompileResult compile(CompileInformation info, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        bytes.addAll(ByteUtil.split(TileBytes.EQUALS_CONDITION));

        // Line number parameter
        bytes.addAll(ByteUtil.splitTrim(info.lineNumber));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Block end specifier
        int blockEndSpecifierPosition = bytes.size();
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Tab count specifier
        bytes.addAll(ByteUtil.splitTrim(info.tabCount));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // First parameter
        TileCompiler.addParameterWithIdentifier(bytes, groups[0]);

        // Second parameter
        TileCompiler.addParameterWithIdentifier(bytes, groups[1]);

        return CompileResult.emptyTrailer(blockEndSpecifierPosition, bytes);
    }
}