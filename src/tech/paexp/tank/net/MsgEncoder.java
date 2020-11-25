package tech.paexp.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author expev
 */
public class MsgEncoder extends MessageToByteEncoder<TankJoinMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg, ByteBuf byteBuf) throws Exception {
        byte[] bytes = tankJoinMsg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
