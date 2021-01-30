package com.karpuzdev.parcel.lang.tiles.executors;

import com.karpuzdev.parcel.lang.helpers.ExecutionInfo;
import com.karpuzdev.parcel.lang.helpers.ExecutionResult;

public abstract class ByteExecutor {

    public abstract short getIdentifier();
    public abstract ExecutionResult execute(ExecutionInfo info);

}