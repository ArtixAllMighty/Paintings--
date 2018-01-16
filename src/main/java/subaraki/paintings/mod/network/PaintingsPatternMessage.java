package subaraki.paintings.mod.network;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsIgnore;
import subaraki.paintings.mod.PaintingsPattern;
import subaraki.paintings.mod.client.PaintingsTextureHandler;

public class PaintingsPatternMessage implements IMessage {

    public String json;

    public PaintingsPatternMessage() {
        this.json = "";
    }

    public PaintingsPatternMessage(String patternJson) {
        this.json = patternJson;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(this.json.getBytes());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.json = buf.toString();
    }

    public static class Handler implements IMessageHandler<PaintingsPatternMessage, IMessage> {

        public Handler() {
        }

        @Override
        public IMessage onMessage(PaintingsPatternMessage message, MessageContext context) {

            Paintings.log.info(message.getClass().getName() + " received.");

//            Gson gson = new Gson();
//            PaintingsPattern.instance = gson.fromJson(message.json, PaintingsPattern.class);
//            PaintingsIgnore.ignoreVanillaPaintings();
//            PaintingsPattern.instance.parseJson();
//            PaintingsTextureHandler.loadPaintingsTexture();

            return null;
        }
    }

}
