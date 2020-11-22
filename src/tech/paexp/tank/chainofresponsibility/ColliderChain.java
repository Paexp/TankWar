package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;
import tech.paexp.tank.PropertyMgr;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author expev
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders;

    public ColliderChain() {
        initColliders();
    }

    private void initColliders() {
        colliders = new ArrayList<>();
        String[] collidersNames = PropertyMgr.get("colliders").split(",");
        for (String collidersName : collidersNames) {
            try {
                Class clazz = Class.forName("tech.paexp.tank.chainofresponsibility." + collidersName);
                Collider collider = (Collider) clazz.getConstructor().newInstance();
                colliders.add(collider);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2) {
        for (Collider collider : colliders) {
            if (!collider.collide(gameObject1, gameObject2)) {
                return false;
            }
        }
        return true;
    }
}
