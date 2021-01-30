package com.karpuzdev.parcel.lang.compilers;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class ReturnCompiler extends TileCompiler {

    @Override
    public List<String> getMatchers() {
        return Arrays.asList("^return$", "^stop$");
    }

    @Override
    public CompileResult compile(CompileInformation info, String[] groups) {
        List<Byte> bytes = new Vector<>(10, 10);

        /*
        Normally we would add a line number parameter but currently,
        return action cannot throw any exceptions
         */
        bytes.addAll(ByteUtil.split(TileBytes.RETURN_ACTION));

        CompileResult result = new CompileResult(bytes);

        return result;
    }
}