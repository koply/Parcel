package com.karpuzdev.parcel.lang.exceptions;

public final class TileException extends RuntimeException {

    public TileException(String message) {
        super(message);
    }

    public TileException(String fileName, String message) {
        super("In file " + fileName + ": " + message);
    }

    public TileException(String fileName, int lineNumber, String message) {
        super("In file " + fileName + " - At line " + lineNumber + ": " + message);
    }
}