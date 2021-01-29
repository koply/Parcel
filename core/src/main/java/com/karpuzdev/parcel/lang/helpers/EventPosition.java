package com.karpuzdev.parcel.lang.helpers;

import java.io.File;

public final class EventPosition {

    public final File file;
    public final int position;

    public EventPosition(File file, int position) {
        this.file = file;
        this.position = position;
    }

    @Override
    public String toString() {
        return "EventPosition{" +
                "file=" + file +
                ", position=" + Integer.toHexString(position) +
                '}';
    }
}