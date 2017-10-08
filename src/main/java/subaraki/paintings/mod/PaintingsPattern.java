package subaraki.paintings.mod;

import java.util.HashMap;

import net.minecraftforge.common.util.EnumHelper;

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

    public void loadPatterns() {
        Integer width = this.pattern[0].length();
        Integer height = this.pattern.length;
        Integer count = 0;

        // Initialize counters
        for (Size size : key.values()) {
            this.sizeCounts.put(size, 0);
        }

        // Iterate through symbols
        for (int offsetY = 0; offsetY < height; offsetY++) {
            for (int offsetX = 0; offsetX < width; offsetX++) {
                String symbol = this.pattern[offsetY].substring(offsetX, offsetX + 1);

                if (symbol.equals(" ")) {
                    continue;
                }

                Size size = this.key.get(symbol);
                if (size != null) {
                    this.addPainting(size, offsetX, offsetY);
                    this.markPaintingAdded(size, offsetX, offsetY);
                    count++;
                } else {
                    Paintings.log.error(String.format("Error processing pattern at offset: %d, %d\n", offsetX, offsetY));
                }
            }
        }

        Paintings.log.info(String.format("%d paintings found in %s/%s.", count, this.type, this.name));
        Paintings.log.info(this.sizeCounts.toString());
    }

    /**
     * Get the size of the entire texture
     * @return Size of textures, in blocks
     */
    public Size getSize() {
        return new Size(
                this.pattern[0].length(),
                this.pattern.length
        );
    }

    /**
     * Add a painting to Minecract from the pattern
     * @param size    Size in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void addPainting(Size size, Integer offsetX, Integer offsetY) {

        Number sizeCount = this.sizeCounts.get(size);
        this.sizeCounts.put(size, sizeCount.intValue() + 1);

        EnumHelper.addArt(
                String.format("EnumArt_%d", PaintingsPattern.enumCounter++),
                String.format("ptg_%d_%d_%d", size.width, size.height, sizeCount.intValue()),
                size.width * 16,
                size.height * 16,
                offsetX * 16,
                offsetY * 16
        );
        Paintings.log.info(String.format("Added %s painting at [%d,%d].", size, offsetX, offsetY));
    }

    /**
     * Remove a painting from the pattern
     * @param size    Size in blocks
     * @param offsetX Left offset in blocks
     * @param offsetY Top offset in blocks
     */
    private void markPaintingAdded(Size size, Integer offsetX, Integer offsetY) {
        if (this.pattern[offsetY].length() < offsetX + size.width) {
            Paintings.log.warn("Added painting extends beyond pattern dimensions.");
        }

        for (int row = offsetY; row < offsetY + size.height; row++) {
            byte[] rowBytes = this.pattern[row].getBytes();
            for (int column = offsetX; column < offsetX + size.width; column++) {
                rowBytes[column] = ' ';
            }
            this.pattern[row] = new String(rowBytes);
        }
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
