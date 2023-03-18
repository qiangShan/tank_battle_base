package com.mashibing.cor;

import com.mashibing.mediator.GameObject;

public interface Collider {

    boolean collide(GameObject o1,GameObject o2);
}
