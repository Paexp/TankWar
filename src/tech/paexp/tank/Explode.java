package tech.paexp.tank;

import java.awt.*;

/**
 * @author expev
 */
public class Explode extends AbstractGameObject{
    private int x, y;
    private int width, height;
    private int step = 0;

    private boolean live = true;

    @Override
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean over) {
        this.live = over;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();

        new Thread() {
            @Override
            public void run() {
                new Audio("audio/explode.wav").play();
            }
        }.start();
    }

    @Override
    public void paint(Graphics g) {
        if (!live) {
            return;
        }

        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;

        if (step >= ResourceMgr.explodes.length) {
            this.die();
        }
    }

    private void die() {
        this.live = false;
    }
}
