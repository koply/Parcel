package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.compilers.EqualsCompiler;
import com.karpuzdev.parcel.lang.compilers.OnCommandCompiler;
import com.karpuzdev.parcel.lang.compilers.RespondTextCompiler;
import com.karpuzdev.parcel.lang.compilers.ReturnCompiler;
import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.internal.IParcelSource;
import com.karpuzdev.parcel.lang.tiles.executors.EqualsExecutor;
import com.karpuzdev.parcel.lang.tiles.executors.RespondTextExecutor;
import com.karpuzdev.parcel.lang.tiles.executors.ReturnExecutor;
import com.karpuzdev.parcel.lang.tiles.scanners.OnCommandScanner;
import com.karpuzdev.parcel.lang.util.ByteUtil;

import java.io.File;
import java.util.List;

// user-written files are "parcel"s
// compiled pieces are "tile"s

/**
 * Everything starts here...
 */
public final class ParcelAPI {

    // TODO: Maybe make the registering automatic
    static {
        ExpressionMatcher.registerCompiler(new OnCommandCompiler());
        ExpressionMatcher.registerCompiler(new RespondTextCompiler());
        ExpressionMatcher.registerCompiler(new ReturnCompiler());
        ExpressionMatcher.registerCompiler(new EqualsCompiler());

        TileScanner.registerScanner(new OnCommandScanner());

        TileExecutor.registerExecutor(new RespondTextExecutor());
        TileExecutor.registerExecutor(new ReturnExecutor());
        TileExecutor.registerExecutor(new EqualsExecutor());
    }

    /*  Static build method to launch the core  */
    public static void build(IParcelSource source, File outputFolder) {
        if (source == null) throw new IllegalArgumentException("IParcelSource parameter cannot be null");
        if (source.getParcels() == null || source.getParcels().length == 0) throw new IllegalArgumentException("Parcels are empty");

        if (outputFolder == null || !outputFolder.exists()) throw new IllegalArgumentException("Output folder could not be found");
        if (outputFolder.isFile()) throw new IllegalArgumentException("Output folder cannot be a file");

        File[] parcels = source.getParcels();

        ParcelCompiler.compileAll(parcels, outputFolder);
        TileLoader.loadAll(outputFolder.listFiles(), true);
    }

    public static String compileCodeToString(String code) {
        List<Byte> bytes = ParcelCompiler.compileCode(code, "test.parcel");

        return ByteUtil.toPrettyString(bytes);
    }

    public static void compileCodeToFile(String code, File outputFolder) {
        ParcelCompiler.compileCode(code, "test.parcel", outputFolder);
    }

    public static void loadTile(File file) {
        TileLoader.load(file);
    }

    public static void executeEvent(EventIdentifier identifier) {
        TileExecutor.execute(identifier);
    }

    public static void debugTile(File file) {
        loadTile(file);
        TileScanner.debug();
    }

}