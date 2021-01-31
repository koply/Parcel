package com.karpuzdev.parcel.lang.tiles;

import com.karpuzdev.parcel.lang.helpers.Flags;

/**
 * Holds state for the current execution flow
 */
public final class ExecutionState {

    public static final int ELSE_FLAG_MASK = 1;

    private final Flags flags = new Flags();
    private long counterRegister = 0;
    private int elseTabCount = 0;

    public boolean checkElseFlag(int tabCount) {
        return this.flags.isSet(ELSE_FLAG_MASK) && this.elseTabCount == tabCount;
    }

    public void setElseFlag(int tabCount) {
        this.flags.set(ELSE_FLAG_MASK);
        this.elseTabCount = tabCount;
    }

    public void clearElseFlag() {
        this.flags.clear(ELSE_FLAG_MASK);
        this.elseTabCount = 0;
    }

    public void setCounter(long num) {
        this.counterRegister = num-1;
    }

    public void decrementCounter() {
        this.counterRegister -= 1;
    }

    public boolean checkCounterZero() {
        return this.counterRegister == 0;
    }

}