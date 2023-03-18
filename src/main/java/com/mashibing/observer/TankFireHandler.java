package com.mashibing.observer;

import com.mashibing.tank.Tank;

public class TankFireHandler implements TankFireObserver {

    @Override
    public void actionOnFire(TankFireEvent e){
        Tank tank=e.getSource();
        tank.fire();
    }
}
