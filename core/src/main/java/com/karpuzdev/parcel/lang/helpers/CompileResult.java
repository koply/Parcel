package com.karpuzdev.parcel.lang.helpers;

import java.util.ArrayList;
import java.util.List;

public final class CompileResult {

    public final int blockEndSpecifierPosition;
    public final List<Byte> bytes;
    public final List<Byte> trailerBytes;

    public CompileResult(int blockEndSpecifierPosition, List<Byte> bytes, List<Byte> trailerBytes) {
        this.blockEndSpecifierPosition = blockEndSpecifierPosition;
        this.bytes = bytes;
        this.trailerBytes = trailerBytes;
    }

    public CompileResult(int blockEndSpecifierPosition, List<Byte> bytes) {
        this(blockEndSpecifierPosition, bytes, null);
    }

    public CompileResult(List<Byte> bytes) {
        this(0, bytes);
    }

    public static CompileResult emptyTrailer(int blockEndSpecifierPosition, List<Byte> bytes) {
        return new CompileResult(blockEndSpecifierPosition, bytes, new ArrayList<>());
    }
}