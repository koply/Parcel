package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.internal.IParcelSource;

import java.io.File;

// user-written files are "parcel"s
// compiled pieces are "tile"s
public final class ParcelAPI {

    /*  Static build method for launch the core  */
    public static void build(IParcelSource source) {
        if (source == null) throw new IllegalArgumentException("IParcelSource parameter cannot be null");
        if (source.getParcels() == null || source.getParcels().length == 0) throw new IllegalArgumentException("Parcels are empty");

        File[] parcels = source.getParcels();

        ParcelLoader.load(parcels);
    }

}