package com.karpuzdev.parcel.lang.helpers;

public final class ParsedParameter {

    public final int newPosition;
    public final ParameterType type;

    public final String stringParam;
    public final long numberParam;
    public final double decimalParam;

    public ParsedParameter(int newPosition, String stringParam) {
        this.newPosition = newPosition;
        this.type = ParameterType.STRING;

        this.stringParam = stringParam;
        this.numberParam = 0;
        this.decimalParam = 0;
    }

    public ParsedParameter(int newPosition, long numberParam) {
        this.newPosition = newPosition;
        this.type = ParameterType.NUMBER;

        this.stringParam = null;
        this.numberParam = numberParam;
        this.decimalParam = 0;
    }

    public ParsedParameter(int newPosition, double decimalParam) {
        this.newPosition = newPosition;
        this.type = ParameterType.DECIMAL;

        this.stringParam = null;
        this.numberParam = 0;
        this.decimalParam = decimalParam;
    }

}