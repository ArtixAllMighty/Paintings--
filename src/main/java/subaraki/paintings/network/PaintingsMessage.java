package subaraki.paintings.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PaintingsMessage implements IMessage {

    public static class PaintingsMessageHandler implements IMessageHandler<PaintingsMessage, IMessage> {

        @Override public IMessage onMessage(PaintingsMessage message, MessageContext ctx) {
            switch(ctx.side) {
                case CLIENT:
                    break;
                case SERVER:
                    break;
            }
            return message;
        }
    }

    public enum PaintingsMessageType {
        NULL,
        CONF
    }

    private PaintingsMessageType type;
    private String value;

    public PaintingsMessage(){}
    public PaintingsMessage(PaintingsMessageType type, String value) {
        this();
        this.type = type;
        this.value = value;
    }

    @Override public void toBytes(ByteBuf buffer) {
        if (this.type != PaintingsMessageType.NULL) {
            String message = String.format("%s::%s", type.toString(), value);
            buffer.writeBytes(message.getBytes());
        }
    }

    @Override public void fromBytes(ByteBuf buffer) {
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);

        String[] message = new String(bytes).split("::");

        if (message.length == 1) {
            this.type = PaintingsMessageType.valueOf(message[0]);
            this.value = message[1];
        } else {
            this.type = PaintingsMessageType.NULL;
            this.value = null;
        }
    }
}
