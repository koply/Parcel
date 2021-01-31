package com.karpuzdev.parcel.lang.compilers;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class LoopTimesCompiler extends TileCompiler {

    @Override
    public List<String> getMatchers() {
        return Arrays.asList("^loop (\\d+) times$");
    }

    @Override
    public CompileResult compile(CompileInformation info, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        bytes.addAll(ByteUtil.split(TileBytes.SETCOUNTER_ACTION));

        // Line parameter
        bytes.addAll(ByteUtil.splitTrim(info.lineNumber));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Times parameter
        int times = Integer.parseInt(groups[0]);
        bytes.addAll(ByteUtil.splitTrim(times));
        bytes.add(TileBytes.NULL_TERMINATOR);

        // Loop start
        int loopStart = bytes.size();

        // Bytes end

        List<Byte> trailerBytes = new Vector<>(10, 10);

        trailerBytes.addAll(ByteUtil.split(TileBytes.JUMPCOUNTER_CONDITION));

        // Line parameter
        trailerBytes.addAll(ByteUtil.splitTrim(info.lineNumber));
        trailerBytes.add(TileBytes.NULL_TERMINATOR);

        // Trailer block end offset
        int trailerBlockEndOffset = trailerBytes.size();
        trailerBytes.add(TileBytes.NULL_TERMINATOR);

        return new CompileResult(bytes, trailerBytes, trailerBlockEndOffset, loopStart);
    }
}