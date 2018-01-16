package subaraki.paintings.mod.network;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsPattern;

public class PaintingsMessage implements IMessage {

    public enum MessageType {
        NULL,
        REQ_PATTERN,
        RESP_INVALID_MESSAGE
    }

    private MessageType messageType;

    public PaintingsMessage() {
        this.messageType = MessageType.NULL;
    }

    public PaintingsMessage(MessageType message) {
        this.messageType = message;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(messageType.toString().getBytes());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.messageType = MessageType.valueOf(buf.toString());
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public static class Handler implements IMessageHandler<PaintingsMessage, IMessage> {

        public Handler() {
        }

        @Override
        public IMessage onMessage(PaintingsMessage message, MessageContext context) {

            Paintings.log.info(message.messageType.toString() + " received by " + context.side.toString());
            return null;

//            if (message.getMessageType() == PaintingsMessage.MessageType.REQ_PATTERN) {
//
//                // Request for loaded pattern
//                Gson gson = new Gson();
//                return new PaintingsPatternMessage((new Gson()).toJson(PaintingsPattern.instance, PaintingsPattern.class));
//
//            } else {
//
//                // Invalid request
//                return new PaintingsMessage(PaintingsMessage.MessageType.RESP_INVALID_MESSAGE);
//
//            }

        }
    }
}
