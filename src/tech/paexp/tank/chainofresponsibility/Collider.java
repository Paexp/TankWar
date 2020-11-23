package tech.paexp.tank.chainofresponsibility;

import tech.paexp.tank.AbstractGameObject;

import java.io.Serializable;

public interface Collider extends Serializable {
    // return true: chain go on, return false: chain break;
    public boolean collide(AbstractGameObject gameObject1, AbstractGameObject gameObject2);
}
