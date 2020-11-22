package tech.paexp.tank;

import java.awt.*;

/**
 * @author expev
 */
public abstract class AbstractGameObject {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
