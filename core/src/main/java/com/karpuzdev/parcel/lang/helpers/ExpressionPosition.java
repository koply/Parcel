package com.karpuzdev.parcel.lang.helpers;

public final class ExpressionPosition {

    public final String fileName;
    public final long line;

    public ExpressionPosition(String fileName, long line) {
        this.fileName = fileName;
        this.line = line;
    }
}