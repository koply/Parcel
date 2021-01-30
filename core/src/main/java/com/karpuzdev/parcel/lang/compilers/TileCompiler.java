package com.karpuzdev.parcel.lang.compilers;

import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.util.List;

/**
 * Compiles parcel lines to tile bytes
 */
public abstract class TileCompiler {

    public abstract List<String> getMatchers();
    public abstract CompileResult compile(CompileInformation info, String[] groups);

    protected static void addParameterWithIdentifier(List<Byte> bytes, String group) {
        // TODO: Sanitize String parameters
        if (group.startsWith("\"")) {
            // Paramter is a string
            bytes.add(TileBytes.STRING_IDENTIFIER);
            bytes.addAll(ByteUtil.split(group.substring(1, group.length()-1)));
            bytes.add(TileBytes.NULL_TERMINATOR);

        } else {
            // Don't look at me like that
            // It's not my problem that java is a complete dick
            try {
                // Parameter is number
                long number = Long.parseLong(group);

                bytes.add(TileBytes.NUMBER_IDENTIFIER);
                bytes.addAll(ByteUtil.split(number));
                bytes.add(TileBytes.NULL_TERMINATOR);
            } catch (NumberFormatException ex) {
                try {
                    // Parameter is a decimal point number
                    double number = Double.parseDouble(group);

                    bytes.add(TileBytes.DECIMAL_IDENTIFIER);
                    bytes.addAll(ByteUtil.split(number));
                    bytes.add(TileBytes.NULL_TERMINATOR);
                } catch (NumberFormatException ex2) {
                    // Parameter has to be a property
                    // TODO: Match property
                }
            }
        }
    }

}