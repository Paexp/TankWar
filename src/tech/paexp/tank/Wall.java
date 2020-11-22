package tech.paexp.tank;

import java.awt.*;

/**
 * @author expev
 */
public class Wall extends AbstractGameObject {
    private int x, y, w, h;
    private Rectangle rectangle;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        rectangle = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return rectangle;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", rectangle=" + rectangle +
                '}';
    }

    @Override
    public boolean isLive() {
        return true;
    }
}
