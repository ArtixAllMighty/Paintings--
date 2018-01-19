package subaraki.paintings.mod.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityPainting;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.CommonProxy;
import subaraki.paintings.mod.client.PaintingsTextureHandler;
import subaraki.paintings.mod.client.RenderPaintingLate;
import subaraki.paintings.mod.network.PaintingsMessage;

public class ClientProxy extends CommonProxy {
    public void registerRenderInformation() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPainting.class, RenderPaintingLate::new);
    }

    public void handlePatternMessage(PaintingsMessage message) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ConfigurationHandler.getInstance().setPattern(new String(message.payload));
            PaintingsTextureHandler.updatePaintingGui();
            RenderPaintingLate.resetTexture();
        });
    }

    public void handleEmptyPatternMessage() {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            // The server in Single Player doesn't read the pattern
            ConfigurationHandler.getInstance().loadPatternSource(Side.CLIENT);
            PaintingsTextureHandler.updatePaintingGui();
            RenderPaintingLate.resetTexture();
        });
    }
}