package subaraki.paintings.mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.Paintings;

import java.nio.charset.Charset;

public class PaintingsMessage implements IMessage {

    public enum MessageType {
        PATTERN_JSON,
        PATTERN_NULL
    }

    public MessageType type;
    public byte[] payload;

    public PaintingsMessage() {
        this.payload = null;
    }

    public PaintingsMessage(MessageType type) {
        this.type = type;
        this.payload = null;
    }

    public PaintingsMessage(MessageType type, String payload) {
        this.type = type;
        this.payload = payload.getBytes();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.type.name().length());
        buf.writeBytes(this.type.name().getBytes());

        if (payload == null) {
            buf.writeInt(0);
        } else {
            buf.writeInt(this.payload.length);
            buf.writeBytes(this.payload);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Integer messageLength = buf.readInt();
        this.type = MessageType.valueOf(buf.readBytes(messageLength).toString(Charset.defaultCharset()));

        Integer payloadLength = buf.readInt();
        if (payloadLength > 0) {
            this.payload = new byte[payloadLength];
            ByteBuf bytes = buf.readBytes(payloadLength);
            bytes.readBytes(payload);
        }
    }

    public static class Handler implements IMessageHandler<PaintingsMessage, IMessage> {

        public Handler() {}

        @Override
        public IMessage onMessage(PaintingsMessage message, MessageContext context) {

            switch (message.type) {
                case PATTERN_JSON:
                    Paintings.proxy.handlePatternMessage(message);
                    break;
                case PATTERN_NULL:
                    Paintings.proxy.handleEmptyPatternMessage();
                    break;
                default:
                    // Should we throw an error or something?
                    break;
            }

            return null;
        }
    }

}
