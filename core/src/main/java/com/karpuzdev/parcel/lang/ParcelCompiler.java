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
        Stack<Integer> trailerEndOffsets = new Stack<>();
        Stack<Integer> trailerEndPositions = new Stack<>();

        Map<Integer, Integer> blockEnds = new HashMap<>();

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

                saveBlockEnds(bytes, blockEnds, blockEndSpecifiers, trailerBytesStack, trailerEndOffsets, trailerEndPositions, shiftTabs);

                if (tabCount == 0) {
                    insertBlockEnds(bytes, blockEnds);
                }
            }

            if (tabCount == currentLevel) {
                levelComplete = true;
            } else {
                currentLevel = tabCount;
            }

            CompileInformation info = new CompileInformation(line.trim(), lineNumber, bytes.size(), tabCount);
            CompileResult result = ExpressionMatcher.compile(info);
            if (result == null) {
                throw new CompilationException(fileName, lineNumber, "Could not understand expression");
            }

            int specifierPos = result.blockEndSpecifierPosition;
            if (specifierPos != -1) {
            }

            if (result.trailerBytes != null) {
                int blockEnd = bytes.size() + specifierPos;
                if (specifierPos == -1) blockEnd = -1;

                blockEndSpecifiers.push(blockEnd);

                trailerBytesStack.push(result.trailerBytes);
                trailerEndOffsets.push(result.trailerBlockEndOffset);
                trailerEndPositions.push(bytes.size() + result.trailerBlockEnd);
            }

            bytes.addAll(result.bytes);
        }

        saveBlockEnds(bytes, blockEnds, blockEndSpecifiers, trailerBytesStack, trailerEndOffsets, trailerEndPositions);
        insertBlockEnds(bytes, blockEnds);

        return bytes;
    }

    // I don't even know wtf is going on beyond this point
    // I had to threaten my brain with a knife to come up with this
    private static void saveBlockEnds(List<Byte> bytes, Map<Integer, Integer> blockEnds,
                                      Stack<Integer> blockEndSpecifiers, Stack<List<Byte>> trailerBytesStack,
                                      Stack<Integer> trailerEndOffsets, Stack<Integer> trailerEnds) {
        saveBlockEnds(bytes, blockEnds, blockEndSpecifiers, trailerBytesStack, trailerEndOffsets, trailerEnds, -1);
    }

    private static void saveBlockEnds(List<Byte> bytes, Map<Integer, Integer> blockEnds,
                                      Stack<Integer> blockEndSpecifiers, Stack<List<Byte>> trailerBytesStack,
                                      Stack<Integer> trailerEndOffsets, Stack<Integer> trailerEnds, int shiftTabs) {
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

            int trailerSpec = trailerEndOffsets.pop();

            if (trailerSpec != -1) {
                int trailerEnd = trailerEnds.pop();
                blockEnds.put(bytes.size() + trailerSpec, trailerEnd-1);
            }

            List<Byte> trailerBytes = trailerBytesStack.pop();

            bytes.addAll(trailerBytes);

            int pos = blockEndSpecifiers.pop();

            if (pos != -1) blockEnds.put(pos, bytes.size());
        }
    }

    private static void insertBlockEnds(List<Byte> bytes, Map<Integer, Integer> blockEnds) {
        Map<BlockKey, BlockEnd> map = new HashMap<>();

        for (var entry : blockEnds.entrySet()) {
            List<Byte> endBytes = ByteUtil.splitTrim(entry.getValue()+1);
            map.put(new BlockKey(entry.getKey()), new BlockEnd(entry.getValue()+1, endBytes));
        }

        Map<Integer, Integer> prevAdd = new HashMap<>();
        Map<Integer, Integer> prevAddTemp = new HashMap<>();

        boolean changed = false;

        do {
            for (var entry1 : map.entrySet()) {
                for (var entry2 : map.entrySet()) {
                    if (entry1.equals(entry2)) continue;

                    // Placing our specifier bytes will change entry2's ending
                    if (entry1.getKey().currentSpec < entry2.getValue().endPos) {
                        List<Byte> endBytes = ByteUtil.splitTrim(entry1.getValue().endPos);

                        entry2.getValue().endPos -= prevAdd.getOrDefault(entry1.getKey().currentSpec, 0);
                        entry2.getValue().endPos += endBytes.size();

                        if (entry1.getKey().currentSpec < entry2.getKey().currentSpec) {
                            entry2.getKey().currentSpec -= prevAdd.getOrDefault(entry1.getKey().firstSpec, 0);
                            entry2.getKey().currentSpec += endBytes.size();
                        }

                        prevAddTemp.put(entry1.getKey().firstSpec, endBytes.size());
                        continue;
                    }

                    if (entry1.getKey().currentSpec < entry2.getKey().currentSpec) {
                        List<Byte> endBytes = ByteUtil.splitTrim(entry1.getValue().endPos);

                        entry2.getKey().currentSpec -= prevAdd.getOrDefault(entry1.getKey().firstSpec, 0);
                        entry2.getKey().currentSpec += endBytes.size();

                        prevAddTemp.put(entry1.getKey().firstSpec, endBytes.size());
                    }
                }
            }

            for (var entry : map.entrySet()) {
                List<Byte> currentBytes = ByteUtil.splitTrim(entry.getValue().endPos);
                if (entry.getValue().prevBytes.size() < currentBytes.size()) {
                    changed = true;
                    entry.getValue().prevBytes = currentBytes;
                }
            }
            prevAdd = prevAddTemp;
            prevAddTemp = new HashMap<>();
        } while (changed);

        List<BlockKey> sortedSpecPositions = new ArrayList<>(map.keySet());
        sortedSpecPositions.sort(Comparator.comparingInt(n -> n.currentSpec));

        for (int i = 0; i < sortedSpecPositions.size(); i++) {
            BlockKey key = sortedSpecPositions.get(i);

            blockEnds.remove(key.firstSpec);

            List<Byte> endBytes = ByteUtil.splitTrim(map.get(key).endPos);
            bytes.addAll(key.currentSpec, endBytes);
        }
    }

    private static final class BlockEnd {
        public int endPos;
        public List<Byte> prevBytes;

        public BlockEnd(int endPos, List<Byte> prevBytes) {
            this.endPos = endPos;
            this.prevBytes = prevBytes;
        }
    }

    private static final class BlockKey {
        public int currentSpec;
        public final int firstSpec;

        public BlockKey(int firstSpec) {
            this.currentSpec = firstSpec;
            this.firstSpec = firstSpec;
        }
    }
}