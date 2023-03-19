package com.mashibing.cor;

import com.mashibing.mediator.GameObject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider{

    private List<Collider> colliders=new LinkedList<>();

    public ColliderChain(){
        /**
        try {
            Object o = Class.forName(String.valueOf(colliders)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */

        add(new BulletTankCollider());
        add(new TankTankCollider());
        add(new BulletWallCollider());
        add(new TankWallCollider());
    }

    public void add(Collider collider){
        colliders.add(collider);
    }

    public boolean collide(GameObject o1, GameObject o2) {
        for(int i=0;i<colliders.size();i++){
            if(!colliders.get(i).collide(o1,o2)){
                return false;
            }
        }
        return true;
    }
}
