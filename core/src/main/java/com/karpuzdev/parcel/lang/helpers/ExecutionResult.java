package com.karpuzdev.parcel.lang.helpers;

public final class ExecutionResult {

    public final boolean shouldStop;
    public final int expressionEnd;

    public ExecutionResult(boolean shouldStop) {
        this.shouldStop = shouldStop;

        // If the execution should halt, we don't need this
        this.expressionEnd = 0;
    }

    public ExecutionResult(int expressionEnd) {
        this.shouldStop = false;
        this.expressionEnd = expressionEnd;
    }
}