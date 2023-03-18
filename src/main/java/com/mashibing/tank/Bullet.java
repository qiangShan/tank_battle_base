package com.mashibing.tank;

import java.awt.*;

public class Bullet {

    private static final int SPEED=5;
    public static final int WIDTH=ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT=ResourceMgr.bulletD.getHeight();

    private int x,y;
    private Dir dir;
    TankFrame tf=null;


    private boolean living =true;

    public Bullet(int x, int y, Dir dir ,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf=tf;
    }

    public void paint(Graphics g) {

        if(!living){
            tf.bullets.remove(this);
        }

        switch (dir){
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            default:
                break;
        }

        move();
    }

    private void move() {

        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
        if(x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            living =false;
        }

    }

    public void collideWith(Tank tank) {
        Rectangle rect1=new Rectangle(this.x,this.y,WIDTH,HEIGHT);
        Rectangle rect2=new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank.HEIGHT);

        if(rect1.intersects(rect2)){
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.living=false;
    }
}
