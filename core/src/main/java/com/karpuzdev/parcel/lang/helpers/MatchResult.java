package com.karpuzdev.parcel.lang.helpers;

import com.karpuzdev.parcel.lang.expressions.TileExpression;

public final class MatchResult {

    public final TileExpression expression;
    public final String[] groups;

    public MatchResult(TileExpression expression, String[] groups) {
        this.expression = expression;
        this.groups = groups;
    }
}