package com.karpuzdev.parcel.lang.tiles;

import com.karpuzdev.parcel.lang.helpers.Flags;

public final class ExecutionState {

    public static final int ELSE_FLAG_MASK = 1;

    private Flags flags = new Flags();
    private long counterRegister = 0;

    public boolean checkElseFlag() {
        return this.flags.isSet(ELSE_FLAG_MASK);
    }

    public void setElseFlag() {
        this.flags.set(ELSE_FLAG_MASK);
    }

    public void clearElseFlag() {
        this.flags.clear(ELSE_FLAG_MASK);
    }

    public void setCounter(long num) {
        this.counterRegister = num;
    }

    public void decrementCounter() {
        this.counterRegister -= 1;
    }

    public boolean checkCounterZero() {
        return this.counterRegister == 0;
    }

}