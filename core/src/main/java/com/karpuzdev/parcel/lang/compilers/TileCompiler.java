package com.karpuzdev.parcel.lang.compilers;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;

import java.util.List;

/**
 * Compiles parcel lines to tile bytes
 */
public abstract class TileCompiler {

    public abstract List<String> getMatchers();
    public abstract CompileResult compile(CompileInformation info, String[] groups);

}