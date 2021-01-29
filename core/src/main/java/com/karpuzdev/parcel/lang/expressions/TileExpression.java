package com.karpuzdev.parcel.lang.expressions;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;

import java.util.List;

public abstract class TileExpression {

    public abstract List<String> getMatchers();
    public abstract CompileResult compile(CompileInformation info, String[] groups);

}