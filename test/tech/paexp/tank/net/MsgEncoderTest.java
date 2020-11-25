package tech.paexp.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import tech.paexp.tank.Dir;
import tech.paexp.tank.Group;
import tech.paexp.tank.Player;

import java.awt.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MsgEncoderTest {

    @Test
    void encode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(new MsgEncoder());

        Player p = new Player(50, 100, Dir.U, Group.BAD);
        TankJoinMsg tankJoinMsg = new TankJoinMsg(p);

        embeddedChannel.writeOutbound(tankJoinMsg);

        ByteBuf buf = embeddedChannel.readOutbound();

        int length = buf.readInt();

        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assertEquals(33, length);
        assertEquals(50, x);
        assertEquals(100, y);
        assertEquals(Dir.U, dir);
        assertFalse(moving);
        assertEquals(Group.BAD, group);
        assertEquals(p.getId(), id);
    }
}