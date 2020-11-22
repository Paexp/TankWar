package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;

public interface Collider {
    public void collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2);
}
