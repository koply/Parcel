package com.karpuzdev.parcel.lang.helpers;

import java.util.List;

public final class ScanInformation {

    public final List<Byte> bytes;
    public final int position;

    public ScanInformation(List<Byte> bytes, int position) {
        this.bytes = bytes;
        this.position = position;
    }
}