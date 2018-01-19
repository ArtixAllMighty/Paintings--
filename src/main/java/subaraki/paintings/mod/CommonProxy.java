package subaraki.paintings.mod;

import subaraki.paintings.mod.network.PaintingsMessage;

public abstract class CommonProxy {
    public void registerRenderInformation() {}
    public void handlePatternMessage(PaintingsMessage message) {}
    public void handleEmptyPatternMessage() {}
    public void loadPattern() {}
}
