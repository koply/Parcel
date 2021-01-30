package com.karpuzdev.parcel.lang.exceptions;

import com.karpuzdev.parcel.lang.helpers.ExpressionPosition;

public final class TileException extends RuntimeException {

    public TileException(String message) {
        super(message);
    }

    public TileException(String fileName, String message) {
        super("In file " + fileName + ": " + message);
    }

    public TileException(String fileName, long lineNumber, String message) {
        super("In file " + fileName + " - At line " + lineNumber + ": " + message);
    }

    public TileException(ExpressionPosition pos,  String message) {
        this(pos.fileName, pos.line, message);
    }
}