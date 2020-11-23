package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.Bullet;
import tech.paexp.tank.Tank;

import java.awt.*;

/**
 * @author expev
 */
public class TankTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        if (gameObject1 != gameObject2 && gameObject1 instanceof Tank && gameObject2 instanceof Tank) {
            Tank tank1 = (Tank) gameObject1;
            Tank tank2 = (Tank) gameObject2;


            if (!tank1.isLive() || !tank2.isLive()) {
                if (tank1.getRect().intersects(tank2.getRect())) {
                    tank1.back();
                    tank2.back();
                }
            }
        }

        return true;
    }
}
