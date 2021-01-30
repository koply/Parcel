package com.karpuzdev.parcel.lang.helpers;

/**
 * Given to compilers to specify the current line, the line number,
 * and the current byte position.
 * 
 * The current byte position is used for loops to be able to add
 * jump action trailer bytes.
 */
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