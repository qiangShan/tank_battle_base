package com.mashibing.factory;

import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.TankFrame;

import java.awt.*;

public class RectExplode extends BaseExplode{

    private int x,y;
    TankFrame tf;
    private int step=0;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void paint(Graphics g){

        Color color=g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x,y,10*step,10*step);
        step++;
        if(step>5){
            tf.explodes.remove(this);
        }
        g.setColor(color);
    }
}
