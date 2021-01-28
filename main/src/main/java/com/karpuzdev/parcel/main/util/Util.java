package com.karpuzdev.parcel.main.util;

import java.awt.*;
import java.util.Random;

public final class Util {

    public static final Random random = new Random();

    public static Color randomColor() {
        return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }
}