package com.karpuzdev.parcel.lang.helpers;

/**
 * Scanners return this to specify the event, where
 * the code block starts and ends (so scanning can continue
 * with another entry-point)
 */
public final class EventInformation {

    public final EventIdentifier identifier;
    public final int blockStart;
    public final int blockEnd;

    public EventInformation(EventIdentifier identifier, int blockStart, int blockEnd) {
        this.identifier = identifier;
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }
}