package tech.paexp.tank.strategy;

import tech.paexp.tank.*;

/**
 * @author expev
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player player) {
        int bX = player.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int bY = player.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;

        TankFrame.INSTANCE.getGameModel().add(new Bullet(bX, bY, player.getDir(), player.getGroup()));
    }
}
