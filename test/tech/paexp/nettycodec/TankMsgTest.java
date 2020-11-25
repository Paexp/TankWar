package tech.paexp.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankMsgTest {

    @Test
    void decode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(new TankMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);

        embeddedChannel.writeInbound(buf);

        TankMsg tankMsg = embeddedChannel.readInbound();

        assertEquals(5, tankMsg.x);
        assertEquals(8, tankMsg.y);
    }

    @Test
    void encode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(new TankMsgEncoder());

        TankMsg tankMsg = new TankMsg(5, 8);

        embeddedChannel.writeOutbound(tankMsg);

        ByteBuf buf = embeddedChannel.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();

        assertEquals(5, x);
        assertEquals(8, y);
    }
}