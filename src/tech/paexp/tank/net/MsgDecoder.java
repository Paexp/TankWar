package tech.paexp.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author expev
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) {
            return;
        }

        byteBuf.markReaderIndex();

        MsgType msgType = MsgType.values()[byteBuf.readInt()];

        int length = byteBuf.readInt();

        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Msg msg = null;
        msg = (Msg) Class.forName("tech.paexp.tank.net." + msgType.toString() + "Msg")
                .getDeclaredConstructor()
                .newInstance();
        msg.parse(bytes);
        list.add(msg);
    }
}
