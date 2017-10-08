package subaraki.paintings.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import subaraki.paintings.mod.Paintings;

/**
 * The Paintings network handler is only used for the client to get the texture setting from the server
 */
public class PaintingsNetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Paintings.MODID);
}
