package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.Bullet;
import tech.paexp.tank.Tank;

/**
 * @author expev
 */
public class BulletTankCollider implements Collider{
    @Override
    public void collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        if (gameObject1 instanceof Bullet && gameObject2 instanceof Tank) {
            Bullet bullet = (Bullet) gameObject1;
            Tank tank = (Tank) gameObject2;

            bullet.collidesWithTank(tank);
        } else if (gameObject1 instanceof Tank && gameObject2 instanceof Bullet) {
            collide(gameObject2, gameObject1);
        }
    }
}
