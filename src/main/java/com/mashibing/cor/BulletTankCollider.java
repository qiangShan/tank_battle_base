package com.mashibing.cor;

import com.mashibing.mediator.GameObject;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.Tank;

public class BulletTankCollider implements Collider{

    @Override
    public boolean collide(GameObject o1, GameObject o2) {

        if(o1 instanceof Bullet && o2 instanceof Tank){
            Bullet b=(Bullet) o1;
            Tank t=(Tank) o2;
            //TODO copy code from method collideWith
            if(b.getGroup() == t.getGroup())  return false;

            if(b.rect.intersects(t.rect)){
                b.die();
                t.die();
                int eX=t.getX()+Tank.WIDTH/2- Explode.WIDTH/2;
                int eY=t.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
                t.gm.add(new Explode(eX,eY,t.gm));
            }
        }else if(o1 instanceof Tank && o2 instanceof Bullet){
            return collide(o2,o1);
        }
        return true;
    }
}
