package tech.paexp.tank;

import tech.paexp.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author expev
 */
public class Tank extends AbstractGameObject {
    public static final int SPEED = 5;
    private int x, y;
    private Dir dir;
    private boolean moving = true;
    private Group group;
    private boolean live = true;
    private int width, height;
    private UUID id;
    private int oldX, oldY;
    private Rectangle rectangle;
    private Random r = new Random();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        oldX = x;
        oldY = y;
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public Tank(TankJoinMsg tankJoinMsg) {
        this.x = tankJoinMsg.getX();
        this.y = tankJoinMsg.getY();
        this.dir = tankJoinMsg.getDir();
        this.group = tankJoinMsg.getGroup();
        this.moving = tankJoinMsg.isMoving();
        this.id = tankJoinMsg.getId();

        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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

    @Override
    public void paint(Graphics g) {
        if (!this.isLive()) {
            return;
        }

        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
            default:
                break;
        }

        move();
        rectangle.x = x;
        rectangle.y = y;
    }

    private void move() {
        if (!moving) {
            return;
        }

        oldX = x;
        oldY = y;

        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
            default:
                break;
        }

        boundsCheck();

        //randomDir();

//        if (r.nextInt(100) > 95) {
//            fire();
//        }
    }

    private void randomDir() {
        if (r.nextInt(100) > 95) {
            this.dir = Dir.randomDir();
        }
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x + width > TankFrame.GAME_WIDTH || y + height > TankFrame.GAME_HEIGHT) {
            this.back();
        }
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    private void fire() {
        int bX = x + this.width / 2 - Bullet.W / 2;
        int bY = y + this.height / 2 - Bullet.H / 2;
        TankFrame.INSTANCE.getGameModel().add(new Bullet(this.id, bX, bY, dir, group));
    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGameModel().add(new Explode(x, y));
    }

    public Rectangle getRect() {
        return rectangle;
    }
}
