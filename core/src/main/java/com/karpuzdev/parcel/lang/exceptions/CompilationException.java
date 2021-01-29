package com.karpuzdev.parcel.lang.exceptions;

public final class CompilationException extends RuntimeException {

    public CompilationException(String message) {
        super(message);
    }

    public CompilationException(String fileName, int lineNumber, String message) {
        super("In file " + fileName + " - At line " + lineNumber + ": " + message);
    }
}