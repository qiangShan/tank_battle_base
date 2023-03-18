package com.mashibing.strategy;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Tank;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank t) {

        int bX=t.x+Tank.WIDTH/2- Bullet.WIDTH/2;
        int bY=t.y+Tank.HEIGHT/2-Bullet.HEIGHT/2;

        new Bullet(bX, bY, t.dir, t.group ,t.tf);
    }
}
