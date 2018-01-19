package subaraki.paintings.mod;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.network.PaintingsChannel;
import subaraki.paintings.mod.network.PaintingsMessage;

public class PaintingsEventHandler {

    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (!event.player.world.isRemote && event.player instanceof EntityPlayerMP) {

            EntityPlayerMP player = (EntityPlayerMP) event.player;
            String patternSource = ConfigurationHandler.getInstance().getPatternSource();

            if (patternSource != null) {
                Paintings.log.info("Sending message PATTERN_JSON from {}", FMLCommonHandler.instance().getEffectiveSide().toString());
                PaintingsMessage message = new PaintingsMessage(PaintingsMessage.MessageType.PATTERN_JSON, patternSource);
                PaintingsChannel.channel.sendTo(message, player);
            } else {
                // The server in Single Player doesn't read the pattern
                Paintings.log.info("Sending message PATTERN_NULL from {}", FMLCommonHandler.instance().getEffectiveSide().toString());
                PaintingsMessage message = new PaintingsMessage(PaintingsMessage.MessageType.PATTERN_NULL);
                PaintingsChannel.channel.sendTo(message, player);
            }

        }
    }
}
