package com.karpuzdev.parcel.lang.expressions.helpers;

import com.karpuzdev.parcel.lang.expressions.TileExpression;

public final class MatchResult {

    public TileExpression expression;
    public String[] groups;

    public MatchResult(TileExpression expression, String[] groups) {
        this.expression = expression;
        this.groups = groups;
    }
}