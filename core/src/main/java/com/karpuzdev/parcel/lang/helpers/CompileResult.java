package com.karpuzdev.parcel.lang.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Compiler classes use return this to specify the compiled output.
 *
 * This class also specifies the block end specifier byte position. The block end
 * cannot be determined before compiling the rest of the block. So we hold on to
 * this position and come back again to insert the correct block end bytes.
 *
 * This class also specifies if there are trailer bytes to be appended to the end
 * of the block. We hold on to these bytes and add them when the block ends. (Useful
 * for adding the return at the end of events or for making loop jumps)
 */
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