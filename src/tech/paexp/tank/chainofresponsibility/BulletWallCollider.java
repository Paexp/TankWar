package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.Bullet;
import tech.paexp.tank.Wall;

/**
 * @author expev
 */
public class BulletWallCollider implements Collider{

    @Override
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        if (gameObject1 instanceof Bullet && gameObject2 instanceof Wall) {

            Bullet bullet = (Bullet) gameObject1;
            Wall wall = (Wall) gameObject2;
            if (bullet.isLive()) {
                if (bullet.getRect().intersects(wall.getRect())) {
                    bullet.die();
                    return false;
                }
            }
        }else if (gameObject1 instanceof Wall && gameObject2 instanceof Bullet) {
            return collide(gameObject2, gameObject1);
        }

        return true;
    }
}
