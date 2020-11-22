package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.Tank;
import tech.paexp.tank.Wall;

/**
 * @author expev
 */
public class TankWallCollider implements Collider {

    @Override
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        if (gameObject1 instanceof Tank && gameObject2 instanceof Wall) {
            Tank tank = (Tank) gameObject1;
            Wall wall = (Wall) gameObject2;
            if (tank.isLive()) {
                if (tank.getRect().intersects(wall.getRect())) {
                    tank.back();
                    return false;
                }
            }
        } else if (gameObject1 instanceof Wall && gameObject2 instanceof Tank) {
            return collide(gameObject2, gameObject1);
        }

        return true;
    }
}
