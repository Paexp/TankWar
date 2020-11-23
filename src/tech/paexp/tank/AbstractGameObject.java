package tech.paexp.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author expev
 */
public abstract class AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
