package com.karpuzdev.parcel.lang.helpers;

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