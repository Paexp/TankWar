package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.Bullet;
import tech.paexp.tank.Tank;

import java.awt.*;

/**
 * @author expev
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        if (gameObject1 instanceof Bullet && gameObject2 instanceof Tank) {
            Bullet bullet = (Bullet) gameObject1;
            Tank tank = (Tank) gameObject2;

            if (!bullet.isLive() || !tank.isLive()) {
                return false;
            }
            if (bullet.getGroup() == tank.getGroup()) {
                return true;
            }

            Rectangle rectTank = tank.getRect();
            if (bullet.getRect().intersects(rectTank)) {
                bullet.die();
                tank.die();
                return false;
            }
        } else if (gameObject1 instanceof Tank && gameObject2 instanceof Bullet) {
            collide(gameObject2, gameObject1);
        }

        return true;
    }
}
