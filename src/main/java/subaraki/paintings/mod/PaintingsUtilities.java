package subaraki.paintings.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityPainting;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;

public class PaintingsUtilities {
    /**
     * "Removes" existing enum values by changing their sizes to 0
     */
    public static void ignoreVanillaPaintings() {
        // Set all vanilla paintings to nothing

        for (EntityPainting.EnumArt art : EntityPainting.EnumArt.values()) {
            ObfuscationReflectionHelper.setPrivateValue(EntityPainting.EnumArt.class, art, 0, "sizeX", "field_75703_B");
            ObfuscationReflectionHelper.setPrivateValue(EntityPainting.EnumArt.class, art, 0, "sizeY", "field_75704_C");
        }
    }

    /**
     * Prints a list of EnumArt values to the log.
     *
     * @param level The logging level to dump to.
     */
    public static void dumpEnumArt(Level level) {
        for (EntityPainting.EnumArt art : EntityPainting.EnumArt.values()) {
            String output = String.format("%s(\"%s\", %d, %d, %d, %d)", art.name(), art.title, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
            Paintings.log.log(level, output);
        }
    }
}
