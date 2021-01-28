package com.karpuzdev.parcel.lang.expressions;

import com.karpuzdev.parcel.lang.expressions.helpers.CompileResult;

import java.util.List;

public abstract class TileExpression {

    public abstract List<String> getMatchers();
    public abstract CompileResult compile(String line, int lineNumber, String[] groups);

}