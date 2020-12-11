package com.karpuzdev.parcel;

import com.karpuzdev.parcel.internal.IParcel;

public final class Core {

    /*  Static build method for launch the core  */
    public static void build(IParcel iParcel) throws NullPointerException {
        if (iParcel == null) throw new NullPointerException("IParcel parameter is could not be null");
        if (iParcel.getParcels() == null || iParcel.getParcels().length == 0) throw new NullPointerException("Parcels are could not be null");
        new Core(iParcel);
    }

    /*  Constructor and iParcel  */
    private final IParcel iParcel;
    protected Core(IParcel iParcel) {
        this.iParcel = iParcel;
    }



}