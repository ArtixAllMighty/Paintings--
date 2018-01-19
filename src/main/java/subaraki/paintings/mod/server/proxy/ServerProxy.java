package subaraki.paintings.mod.server.proxy;

import net.minecraftforge.fml.relauncher.Side;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.CommonProxy;

public class ServerProxy extends CommonProxy {
    public void loadPattern() {
        ConfigurationHandler.getInstance().loadPatternSource(Side.SERVER);
    }
}
