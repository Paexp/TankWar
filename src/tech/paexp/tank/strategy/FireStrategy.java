package tech.paexp.tank.strategy;

import tech.paexp.tank.Player;

import java.io.Serializable;

/**
 * @author expev
 */
public interface FireStrategy extends Serializable {
    public void fire(Player player);
}
