package com.karpuzdev.parcel.bionic;

import com.karpuzdev.parcel.Core;
import com.karpuzdev.parcel.internal.IParcel;
import me.koply.botanic.bionic.java.Bionic;

import java.io.File;

public class BionicMain extends Bionic implements IParcel {

    @Override
    public void onEnable() {
        System.out.println("Hello World!");
        Core.build(this);
    }

    @Override
    public void onDisable() {
        System.out.println("Byebye World!");
    }

    @Override
    public File[] getParcels() {
        return new File("parcel/parcels/").listFiles();
    }
}