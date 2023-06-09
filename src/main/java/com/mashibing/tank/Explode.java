package com.mashibing.tank;

import java.awt.*;

public class Explode {

    public static int WIDTH=ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT=ResourceMgr.explodes[0].getHeight();

    private int x,y;
    TankFrame tf;
    private int step=0;

    public Explode(int x, int y, TankFrame tf) {
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

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++] , x, y,null);
        if(step>=ResourceMgr.explodes.length){
           tf.explodes.remove(this);
        }

    }
}
