package tech.paexp.tank.strategy;

import tech.paexp.tank.*;
import tech.paexp.tank.net.BulletNewMsg;
import tech.paexp.tank.net.Client;

/**
 * @author expev
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Player player) {
        int bX = player.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int bY = player.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;

        Bullet bullet = new Bullet(player.getId(), bX, bY, player.getDir(), player.getGroup());

        TankFrame.INSTANCE.getGameModel().add(bullet);

        Client.INSTANCE.send(new BulletNewMsg(bullet));
    }
}
