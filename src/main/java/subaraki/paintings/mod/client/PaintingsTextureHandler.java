package subaraki.paintings.mod.client;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsPattern;

import java.lang.reflect.Field;

public class PaintingsTextureHandler {

    @SideOnly(Side.CLIENT)
    public static void registerRenderInformation() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPainting.class, RenderPaintingLate::new);
    }

    @SideOnly(Side.CLIENT)
    public static void updatePaintingGui() {
        final String CLASS_LOC = "com.mcf.davidee.paintinggui.gui.PaintingButton";
        final PaintingsPattern pattern = ConfigurationHandler.getInstance().getPattern();

        // Replace texture used by Painting Gui Selection Revamped
        try {
            Class altClass = Class.forName(CLASS_LOC);
            paintingGuiTextureHelper(altClass, "TEXTURE", new ResourceLocation("subaraki:art/" + pattern.getName() + ".png"));
            paintingGuiHelper(altClass, "KZ_WIDTH", pattern.getSize().width * 16);
            paintingGuiHelper(altClass, "KZ_HEIGHT", pattern.getSize().height * 16);
        } catch (Exception e) {
            Paintings.log.warn(e.getLocalizedMessage());
        }
    }

    private static void paintingGuiHelper(Class c, String field, int value)
            throws Exception {
        Field f = c.getField(field);
        f.setAccessible(true);
        f.set(null, value);
    }

    private static void paintingGuiTextureHelper(Class c, String field, ResourceLocation loc)
            throws Exception {
        Field f = c.getField(field);
        f.setAccessible(true);
        f.set(null, loc);
    }

}
