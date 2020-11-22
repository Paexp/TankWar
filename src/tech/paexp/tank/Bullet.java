package tech.paexp.tank;

import java.awt.*;

/**
 * @author expev
 */
public class Bullet extends AbstractGameObject{
    public static final int SPEED = 6;
    private int x, y;
    private Dir dir;
    private Group group;
    private boolean live = true;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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
    }

    public void collidesWithTank(Tank tank) {
        if (!this.isLive() || !tank.isLive()) {
            return;
        }
        if (this.group == tank.getGroup()) {
            return;
        }

        Rectangle rectBullet = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(),
                ResourceMgr.badTankU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(),
                ResourceMgr.goodTankU.getHeight());
        if (rectBullet.intersects(rectTank)) {
            this.die();
            tank.die();
        }
    }


    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public void die() {
        this.setLive(false);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
