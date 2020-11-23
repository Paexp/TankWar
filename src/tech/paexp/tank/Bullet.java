package tech.paexp.tank;

import java.awt.*;

/**
 * @author expev
 */
public class Bullet extends AbstractGameObject {
    public static final int SPEED = 6;
    public static final int W = ResourceMgr.bulletU.getWidth();
    public static final int H = ResourceMgr.bulletU.getHeight();

    private int x, y;
    private Dir dir;
    private Group group;
    private boolean live = true;

    private Rectangle rectangle;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rectangle = new Rectangle(x, y, W, H);
    }

    @Override
    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            default:
                break;
        }

        move();
    }

    private void move() {
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

        // update the rect
        rectangle.x = x;
        rectangle.y = y;
    }

    public Rectangle getRect() {
        return rectangle;
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public void die() {
        this.setLive(false);
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

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", live=" + live +
                ", rectangle=" + rectangle +
                '}';
    }
}
