package tech.paexp.tank.net;

import tech.paexp.tank.*;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg {
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;

    // self's id
    private UUID id;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.dir = p.getDir();
        this.moving = p.isMoving();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public byte[] toBytes() {

        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(x);
            dataOutputStream.writeInt(y);
            dataOutputStream.writeInt(dir.ordinal());
            dataOutputStream.writeBoolean(moving);
            dataOutputStream.writeInt(group.ordinal());
            dataOutputStream.writeLong(id.getMostSignificantBits());
            dataOutputStream.writeLong(id.getLeastSignificantBits());
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

    public void parse(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();
            this.dir = Dir.values()[dataInputStream.readInt()];
            this.moving = dataInputStream.readBoolean();
            this.group = Group.values()[dataInputStream.readInt()];
            this.id = new UUID(dataInputStream.readLong(), dataInputStream.readLong());

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handle() {
        // if this msg's id equals my tank's id return
        // otherwise add new tank to my collection
        if (this.id.equals(TankFrame.INSTANCE.getGameModel().getMyTank().getId())) {
            return;
        }

        if (TankFrame.INSTANCE.getGameModel().findTankByUUID(this.id) != null) {
            return;
        }

        Tank t = new Tank(this);
        TankFrame.INSTANCE.getGameModel().add(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGameModel().getMyTank()));

    }
}
