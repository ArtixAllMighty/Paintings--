package subaraki.paintings.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityPainting;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import subaraki.paintings.mod.network.PaintingsChannel;
import subaraki.paintings.mod.network.PaintingsMessage;

public class PaintingsEventHandler {

    @SubscribeEvent
    public void entityConstructingEvent(EntityEvent.EntityConstructing event) {
        // We only care about paintings
        if (event.getEntity() instanceof EntityPainting) {
            EntityPainting entity = (EntityPainting) event.getEntity();
            entity.art = EntityPainting.EnumArt.values()[0];
        }
    }

    @SubscribeEvent
    public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        // We only care about paintings
        if (event.getEntity() instanceof EntityPainting) {
            Paintings.log.info(event);
        }
    }

    @SubscribeEvent
    public static void clientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            PaintingsMessage message = new PaintingsMessage(PaintingsMessage.MessageType.REQ_PATTERN);
            PaintingsChannel.channel.sendToServer(message);
        });
    }
}
