package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.exceptions.CompilationException;
import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import com.karpuzdev.parcel.lang.util.ByteUtil;
import com.karpuzdev.parcel.lang.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * General parcel compiling manager
 */
final class ParcelCompiler {

    private ParcelCompiler() { }

    static void compileAll(File[] parcels, File outputFolder) {

        // we don't want duplicate names
        Set<String> names = new HashSet<>();

        for (File parcel : parcels) {
            if (parcel.isDirectory() || parcel.isHidden()) continue;

            // files should be like "hello.parcel" (with .parcel extension)
            if (!FileUtil.getFileExtension(parcel).equals("parcel")) continue;

            if (names.contains(parcel.getName())) throw new IllegalArgumentException(parcel.getName() + " is duplicated. There cannot be duplicate names.");
            names.add(parcel.getName());

            compileFile(parcel, outputFolder);
        }
    }

    static void compileFile(File parcel, File outputFolder) {
        if (parcel.isDirectory() || parcel.isHidden()) throw new IllegalArgumentException("Parcels have to be non-hidden files.");

        // files should be like "hello.parcel" (with .parcel extension)
        if (!FileUtil.getFileExtension(parcel).equals("parcel")) throw new IllegalArgumentException("Parcels have to have .parcel extension.");

        String code = FileUtil.readFile(parcel);
        compileCode(code, parcel.getName(), outputFolder);
    }

    static void compileCode(String code, String name, File outputFolder) {
        try {
            List<Byte> bytes = compileCode(code, name);

            name = name.substring(0, name.lastIndexOf('.'));

            File tile = new File(outputFolder, name + ".tile");
            tile.createNewFile();

            FileUtil.writeFileBytes(tile, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<Byte> compileCode(String code, String fileName) {
        List<Byte> bytes = new Vector<>(10, 10);

        // All tiles start with 4 bytes
        // First 3 bytes are KMN bytes
        bytes.addAll(ByteUtil.splitTrim(TileBytes.TILE_HEADER));
        // The 4th byte is the version specifier
        bytes.add(TileBytes.TILE_VERSION);

        Stack<Integer> blockEndSpecifiers = new Stack<>();
        Stack<List<Byte>> trailerBytesStack = new Stack<>();

        int currentLevel = 0;
        boolean levelComplete = false;

        int lineNumber = 0;

        String[] lines = code.split("\n");

        for (String line : lines) {
            lineNumber++;

            if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                continue;
            }

            int tabCount = 0;
            for (char ch : line.toCharArray()) {
                if (ch != '\t') break;

                tabCount++;
            }

            if (tabCount - currentLevel >= 2) {
                throw new CompilationException(fileName, lineNumber, "Invalid tab usage");
            }

            if (tabCount > currentLevel && !levelComplete) {
                throw new CompilationException(fileName, lineNumber, "Invalid tab usage");
            }

            // A block has to have ended if we went down a level
            if (tabCount < currentLevel) {
                int shiftTabs = (currentLevel - tabCount);

                if (blockEndSpecifiers.empty() || trailerBytesStack.empty()) {
                    throw new CompilationException(fileName, lineNumber, "A block ended without starting");
                }

                handleBlockEnds(bytes, blockEndSpecifiers, trailerBytesStack, shiftTabs);
            }

            if (tabCount == currentLevel) {
                levelComplete = true;
            } else {
                currentLevel = tabCount;
            }

            CompileInformation info = new CompileInformation(line.trim(), lineNumber, bytes.size());
            CompileResult result = ExpressionMatcher.compile(info);
            if (result == null) {
                throw new CompilationException(fileName, lineNumber, "Could not understand expression");
            }

            int specifierPos = result.blockEndSpecifierPosition;
            if (specifierPos != 0) {
                blockEndSpecifiers.push(bytes.size() + specifierPos);
            }

            if (result.trailerBytes != null) {
                trailerBytesStack.push(result.trailerBytes);
            }

            bytes.addAll(result.bytes);
        }

        handleBlockEnds(bytes, blockEndSpecifiers, trailerBytesStack);

        return bytes;
    }

    // I don't even know wtf is going on beyond this point
    // I had to threaten my brain with a knife to come up with this
    private static void handleBlockEnds(List<Byte> bytes, Stack<Integer> blockEndSpecifiers, Stack<List<Byte>> trailerBytesStack) {
        handleBlockEnds(bytes, blockEndSpecifiers, trailerBytesStack, -1);
    }

    private static void handleBlockEnds(List<Byte> bytes, Stack<Integer> blockEndSpecifiers, Stack<List<Byte>> trailerBytesStack, int shiftTabs) {
        List<BlockEnd> ends = new ArrayList<>();

        while (true) {
            if (shiftTabs == 0) {
                break;
            }

            if (shiftTabs > 0) {
                shiftTabs -= 1;

            } else if (trailerBytesStack.empty() || blockEndSpecifiers.empty()) {
                // shiftTabs < 0 means don't check shiftTabs
                break;
            }

            bytes.addAll(trailerBytesStack.pop());

            int pos = blockEndSpecifiers.pop();

            ends.add(new BlockEnd(pos, bytes.size()));
        }

        int lastAdd;
        int add = 0;

        do {
            lastAdd = add;
            add = 0;
            for (BlockEnd blockEnd : ends) {
                List<Byte> endBytes = ByteUtil.splitTrim(blockEnd.endPos + lastAdd);
                add += endBytes.size();
            }
        } while (add != lastAdd);

        for (BlockEnd blockEnd : ends) {
            List<Byte> endBytes = ByteUtil.splitTrim(blockEnd.endPos + lastAdd);
            bytes.addAll(blockEnd.specPos, endBytes);
        }
    }

    private static final class BlockEnd {
        public final int specPos;
        public final int endPos;

        public BlockEnd(int specPos, int endPos) {
            this.specPos = specPos;
            this.endPos = endPos;
        }
    }

}