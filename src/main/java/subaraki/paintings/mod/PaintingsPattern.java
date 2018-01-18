package subaraki.paintings.mod;

import net.minecraftforge.common.util.EnumHelper;

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

    private static Integer enumCounter = 0;

    private String type = "subaraki:pattern";
    private String name = null;
    private String[] pattern = null;
    private HashMap<String, Size> key = null;

    public void parse() {
        Integer width = this.pattern[0].length();
        Integer height = this.pattern.length;
        HashMap<Size, Integer> sizeCounts = new HashMap<Size, Integer>();

        // Use a copy of the pattern so we can keep the original in memory
        String[] workingPattern = this.pattern.clone();

        // Initialize counters
        for (Size size : key.values()) {
            sizeCounts.put(size, 0);
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

                    // Keep separate counts for each distinct size
                    Integer sizeCount = sizeCounts.get(size);
                    this.addPainting(size, offsetX, offsetY, sizeCount);
                    sizeCounts.put(size, ++sizeCount);

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

        Paintings.log.info("Loaded {} pattern", this.name);
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
     * @param size      Size in blocks
     * @param offsetX   Left offset, in blocks, of the painting on the texture
     * @param offsetY   Top offset, in blocks, of the painting on the texture
     * @param sizeIndex A special discriminator that, along with the size, is used to uniquely identify a painting
     */
    private void addPainting(Size size, Integer offsetX, Integer offsetY, Integer sizeIndex) {
        EnumHelper.addArt(
                // Internal runtime field name, not used by the database
                String.format("MOREPAINTINGS_%d", PaintingsPattern.enumCounter++),

                // Identifies entity - this is what is stored in the region file
                String.format("ptg_%d_%d_%d", size.width, size.height, sizeIndex),

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
