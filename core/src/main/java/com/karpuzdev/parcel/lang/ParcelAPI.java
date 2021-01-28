package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.internal.IParcelSource;

import java.io.File;

// user-written files are "parcel"s
// compiled pieces are "tile"s
public final class ParcelAPI {

    /*  Static build method to launch the core  */
    public static void build(IParcelSource source, File outputFolder) {
        if (source == null) throw new IllegalArgumentException("IParcelSource parameter cannot be null");
        if (source.getParcels() == null || source.getParcels().length == 0) throw new IllegalArgumentException("Parcels are empty");

        if (outputFolder == null || !outputFolder.exists()) throw new IllegalArgumentException("Output folder could not be found");
        if (outputFolder.isFile()) throw new IllegalArgumentException("Output folder cannot be a file");

        File[] parcels = source.getParcels();

        ParcelCompiler.compileAll(parcels, outputFolder);
    }

}