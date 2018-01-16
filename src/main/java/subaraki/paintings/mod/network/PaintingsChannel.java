package subaraki.paintings.mod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.paintings.mod.Paintings;

public class PaintingsChannel {
    public static final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(Paintings.MODID);

    public static void registerMessages() {
        int id = 0;

        // Server->Client: PaintingsPattern object
        channel.registerMessage(PaintingsPatternMessage.Handler.class, PaintingsPatternMessage.class, id++, Side.CLIENT);

        // Named message handler
        channel.registerMessage(PaintingsMessage.Handler.class, PaintingsMessage.class, id++, Side.SERVER);
        channel.registerMessage(PaintingsMessage.Handler.class, PaintingsMessage.class, id++, Side.CLIENT);
    }
}
