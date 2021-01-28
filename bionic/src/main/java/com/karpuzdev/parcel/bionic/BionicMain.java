package com.karpuzdev.parcel.bionic;

import com.karpuzdev.parcel.lang.ParcelAPI;
import com.karpuzdev.parcel.lang.internal.IParcelSource;
import me.koply.botanic.bionic.java.Bionic;

import java.io.File;

public class BionicMain extends Bionic implements IParcelSource {

    @Override
    public void onEnable() {
        System.out.println("Hello World!");
        ParcelAPI.build(this);
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