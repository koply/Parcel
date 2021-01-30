package com.karpuzdev.parcel.lang.helpers;

/**
 * Executors return this to specify where to continue
 * and if the execution should stop here (used for return action)
 */
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