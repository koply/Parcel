package com.karpuzdev.parcel.lang.helpers;

import com.karpuzdev.parcel.lang.compilers.TileCompiler;

/**
 * ExpressionMatcher uses this class to group the
 * expression and the parameters
 */
public final class MatchResult {

    public final TileCompiler expression;
    public final String[] groups;

    public MatchResult(TileCompiler expression, String[] groups) {
        this.expression = expression;
        this.groups = groups;
    }
}