package subaraki.paintings.mod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.commons.io.IOUtils;
import subaraki.paintings.config.ConfigurationHandler;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;

public class PaintingsPattern {

    public class Size {
        public Integer width;
        public Integer height;

        Size(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }

        public String toString() {
            return String.format("[%d, %d]", this.width, this.height);
        }
    }

    public static PaintingsPattern instance = null;

    private static Integer enumCounter = 0;

    private String type = "subaraki:pattern";
    private String name = null;
    private String[] pattern = null;
    private HashMap<String, Size> key = null;
    private HashMap<Size, Number> sizeCounts = new HashMap<Size, Number>();


    // Adapted from parseJsonRecipes() in CraftingManager.java
    public static boolean load(String patternName) {
        FileSystem filesystem = null;
        boolean success = true;

        final File configPatternFile = new File(ConfigurationHandler.instance.patternsDirectory, ConfigurationHandler.instance.texture + ".json");

        final String assetsRoot = "/assets/subaraki";

        try {
            Path path = null;

            if (configPatternFile.exists()) {
                // A pattern file for the specified texture exists in the config/morepaintings/paintings folder
                path = configPatternFile.toPath();
            } else {
                // Load file from resources
                URL url = PaintingsPattern.class.getResource(assetsRoot);
                if (url != null) {
                    URI uri = url.toURI();
                    String patternFilename = assetsRoot + "/patterns/" + patternName + ".json";

                    // Generate path
                    switch (uri.getScheme()) {
                        case "file":
                            path = Paths.get(PaintingsPattern.class.getResource(patternFilename).toURI());
                            break;
                        case "jar":
                            filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                            path = filesystem.getPath(patternFilename);
                            break;
                        default:
                            Paintings.log.error("Unsupported scheme " + uri + " trying to get " + patternName + " pattern");
                            break;
                    }
                } else {

                    Paintings.log.error("Couldn't find .mcassetsroot");
                    success = false;

                }
            }

            // Read in JSON
            if (path != null) {

                BufferedReader reader = null;

                try {

                    reader = Files.newBufferedReader(path);
                    Gson gson = new Gson();
                    JsonElement element = gson.fromJson(reader, JsonElement.class);
                    JsonObject json = element.getAsJsonObject();

                    PaintingsPattern.instance = gson.fromJson(json, PaintingsPattern.class);

                } catch (JsonParseException e) {

                    Paintings.log.error("Parsing error loading pattern " + patternName, (Throwable) e);
                    success = false;

                } catch (IOException e) {

                    Paintings.log.error("Couldn't read pattern " + patternName + " from " + path, (Throwable) e);
                    success = false;

                } finally {

                    IOUtils.closeQuietly(reader);

                }
            }

        } catch (IOException | URISyntaxException urisyntaxexception) {

            Paintings.log.error("Couldn't get a list of all recipe files", (Throwable) urisyntaxexception);
            success = false;

        } finally {

            IOUtils.closeQuietly((Closeable) filesystem);

        }

        return success;
    }

    public void parseJson() {
        Integer width = this.pattern[0].length();
        Integer height = this.pattern.length;
        Integer count = 0;

        // Use a copy of the pattern so we can keep the original in memory
        String[] workingPattern = this.pattern.clone();

        // Initialize counters
        for (Size size : key.values()) {
            this.sizeCounts.put(size, 0);
        }

        // Iterate through symbols
        for (int offsetY = 0; offsetY < height; offsetY++) {
            for (int offsetX = 0; offsetX < width; offsetX++) {
                String symbol = workingPattern[offsetY].substring(offsetX, offsetX + 1);

                if (symbol.equals(" ")) {
                    continue;
                }

                Size size = this.key.get(symbol);
                if (size != null) {

                    this.addPainting(size, offsetX, offsetY);
                    count++;

                    // Clear painting from the working pattern
                    if (workingPattern[offsetY].length() < offsetX + size.width) {
                        Paintings.log.warn("Added painting extends beyond pattern dimensions.");
                    }

                    for (int row = offsetY; row < offsetY + size.height; row++) {
                        byte[] rowBytes = workingPattern[row].getBytes();
                        for (int column = offsetX; column < offsetX + size.width; column++) {
                            rowBytes[column] = ' ';
                        }
                        workingPattern[row] = new String(rowBytes);
                    }

                } else {
                    Paintings.log.error(String.format("Error processing pattern at offset: %d, %d\n", offsetX, offsetY));
                }
            }
        }

        Paintings.log.info("Loaded pattern");
    }

    /**
     * Get the size of the entire texture
     *
     * @return Size of textures, in blocks
     */
    public Size getSize() {
        return new Size(
                this.pattern[0].length(),
                this.pattern.length
        );
    }

    public String getName() {
        return this.name;
    }

    /**
     * Add a painting to Minecract from the pattern
     *
     * @param size    Size in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void addPainting(Size size, Integer offsetX, Integer offsetY) {

        // Keep separate counts for each distinct size
        Number sizeCount = this.sizeCounts.get(size);
        this.sizeCounts.put(size, sizeCount.intValue() + 1);

        EnumHelper.addArt(
                // Internal runtime field name, not used by the database
                String.format("MOREPAINTINGS_%d", PaintingsPattern.enumCounter++),

                // Identifies entity - this is what is stored in the region file
                String.format("ptg_%d_%d_%d", size.width, size.height, sizeCount.intValue()),

                // Size and position of sprite on sprite sheet
                size.width * 16,
                size.height * 16,
                offsetX * 16,
                offsetY * 16
        );
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Pattern: " + this.type + "/" + this.name + "\n");
        for (String s : this.pattern) {
            sb.append(String.format("    %s\n", s));
        }
        sb.append(String.format("Key:\n    %s", this.key.toString()));

        return sb.toString();
    }
}
