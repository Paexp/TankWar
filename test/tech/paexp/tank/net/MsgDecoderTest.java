package tech.paexp.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import tech.paexp.tank.Dir;
import tech.paexp.tank.Group;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MsgDecoderTest {

    @Test
    void decode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(33);
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.U.ordinal());
        buf.writeBoolean(true);
        buf.writeInt(Group.BAD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        embeddedChannel.writeInbound(buf);

        TankJoinMsg tankJoinMsg = embeddedChannel.readInbound();

        assertEquals(5, tankJoinMsg.getX());
        assertEquals(8, tankJoinMsg.getY());
        assertEquals(Dir.U, tankJoinMsg.getDir());
        assertEquals(true, tankJoinMsg.isMoving());
        assertEquals(Group.BAD, tankJoinMsg.getGroup());
        assertEquals(id, tankJoinMsg.getId());
    }
}