package com.karpuzdev.parcel.lang.exceptions;

public final class ScanException extends RuntimeException {

    public ScanException(String message) {
        super(message);
    }

    public ScanException(String fileName, String message) {
        super("In file " + fileName + ": " + message);
    }

    public ScanException(String fileName, int lineNumber, String message) {
        super("In file " + fileName + " - At line " + lineNumber + ": " + message);
    }
}