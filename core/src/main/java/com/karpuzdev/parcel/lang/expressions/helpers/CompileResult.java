package com.karpuzdev.parcel.lang.expressions.helpers;

import java.util.List;

public final class CompileResult {

    public int blockEndSpecifierPosition;
    public List<Byte> bytes;

    public CompileResult(int blockEndSpecifierPosition, List<Byte> bytes) {
        this.blockEndSpecifierPosition = blockEndSpecifierPosition;
        this.bytes = bytes;
    }

    public CompileResult(List<Byte> bytes) {
        this(0, bytes);
    }
}