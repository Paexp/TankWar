package tech.paexp.tank;

import tech.paexp.tank.net.Client;
import tech.paexp.tank.net.TankMoveOrDirChangeMsg;
import tech.paexp.tank.net.TankStopMsg;
import tech.paexp.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * @author expev
 */
public class Player extends AbstractGameObject {
    public static final int SPEED = 5;
    private int x, y;
    private Dir dir;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;
    private boolean live = true;
    private FireStrategy strategy = null;
    private UUID id = UUID.randomUUID();

    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        //init fire strategy from config file
        this.initFireStrategy();
    }

    public UUID getId() {
        return id;
    }

    public boolean isMoving() {
        return moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
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

        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(), x, y - 10);
        g.setColor(c);

        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankL: ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankU: ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankR: ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankD: ResourceMgr.goodTankD, x, y, null);
                break;
            default:
                break;
        }

        move();
    }

    public void keyPressed(KeyEvent e) {
        // 根据不同的方向键，移动
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            default:
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        boolean oldMoving = moving;

        Dir oldDir = this.getDir();

        // all dir keys are released, tank should be stop.
        if (!bL && !bU && !bR && !bD) {
            moving = false;
            Client.INSTANCE.send(new TankStopMsg(this.id, this.x, this.y));
        } else {
            // any dir key is pressed, tank should be moving.
            moving = true;

            if (bL && !bU && !bR && !bD) {
                dir = Dir.L;
            }
            if (!bL && bU && !bR && !bD) {
                dir = Dir.U;
            }
            if (!bL && !bU && bR && !bD) {
                dir = Dir.R;
            }
            if (!bL && !bU && !bR && bD) {
                dir = Dir.D;
            }
            if (!oldMoving) {
                Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id, this.x, this.y, this.dir));
            }
            if (!this.dir.equals(oldDir)) {
                Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id, this.x, this.y, this.dir));
            }
        }
    }

    private void move() {
        if (!moving) {
            return;
        }
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
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            default:
                break;
        }

        setMainDir();
    }

    private void initFireStrategy() {
        // 多态
//        ClassLoader loader = Player.class.getClassLoader();
        String className = PropertyMgr.get("tankFireStrategy");
        try {
//            Class clazz = loader.loadClass("tech.paexp.tank.strategy." + className);
            Class clazz = Class.forName("tech.paexp.tank.strategy." + className);
            strategy = (FireStrategy) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void fire() {
        strategy.fire(this);
    }
}
