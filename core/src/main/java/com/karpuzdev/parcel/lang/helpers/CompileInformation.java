package com.karpuzdev.parcel.lang.helpers;

public final class CompileInformation {

    public final String line;
    public final int lineNumber;
    public final int currentPos;

    public CompileInformation(String line, int lineNumber, int currentPos) {
        this.line = line;
        this.lineNumber = lineNumber;
        this.currentPos = currentPos;
    }
}