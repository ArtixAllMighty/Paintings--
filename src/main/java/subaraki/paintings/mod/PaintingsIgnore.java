package subaraki.paintings.mod;

import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.Level;

public class PaintingsIgnore {

    public static void ignoreVanillaPaintings() {
        // Set all vanilla paintings to nothing

        for (EnumArt art : EnumArt.values()) {
            ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, art, 0, "sizeX", "field_75703_B");
            ObfuscationReflectionHelper.setPrivateValue(EnumArt.class, art, 0, "sizeY", "field_75704_C");
        }
    }

    public static void dumpEnumArt(Level level) {
        for (EnumArt art : EnumArt.values()) {
            String output = String.format("%s(\"%s\", %d, %d, %d, %d)", art.name(), art.title, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
            Paintings.log.log(level, output);
        }
    }
}
