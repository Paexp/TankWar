package tech.paexp.tank.net;

import tech.paexp.tank.Bullet;
import tech.paexp.tank.Dir;
import tech.paexp.tank.Group;
import tech.paexp.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @author expev
 */
public class BulletNewMsg extends Msg {
    private UUID playerId;
    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeLong(playerId.getMostSignificantBits());
            dataOutputStream.writeLong(playerId.getLeastSignificantBits());

            dataOutputStream.writeLong(id.getMostSignificantBits());
            dataOutputStream.writeLong(id.getLeastSignificantBits());

            dataOutputStream.writeInt(x);
            dataOutputStream.writeInt(y);
            dataOutputStream.writeInt(dir.ordinal());
            dataOutputStream.writeInt(group.ordinal());

            dataOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.playerId = new UUID(dataInputStream.readLong(), dataInputStream.readLong());
            this.id = new UUID(dataInputStream.readLong(), dataInputStream.readLong());
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();
            this.dir = Dir.values()[dataInputStream.readInt()];
            this.group = Group.values()[dataInputStream.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handle() {
        if (this.playerId.equals(TankFrame.INSTANCE.getGameModel().getMyTank().getId())) {
            return;
        }

        Bullet bullet = new Bullet(this.playerId, x, y, dir, group);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGameModel().add(bullet);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }
}
