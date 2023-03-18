package com.mashibing.factory;

import com.mashibing.tank.*;

import java.awt.*;

public class RectBullet extends BaseBullet {

    private static final int SPEED=10;
    public static final int WIDTH= ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT=ResourceMgr.bulletD.getHeight();

    private int x,y;
    private Dir dir;
    TankFrame tf=null;
    private Group group=Group.BAD;
    Rectangle rect=new Rectangle();


    private boolean living =true;

    public RectBullet(int x, int y, Dir dir , Group group , TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.tf=tf;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;

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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {

        if(!living){
            tf.bullets.remove(this);
        }

        Color color=g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x,y,20,20);
        g.setColor(color);

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

        //update rect
        rect.x=this.x;
        rect.y=this.y;

        if(x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            living =false;
        }

    }

    public void collideWith(BaseTank tank) {
        if(this.group == tank.getGroup())  return;

        if(rect.intersects(tank.rect)){
            tank.die();
            this.die();
            int eX=tank.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
            int eY=tank.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
            tf.explodes.add(tf.gf.createExplode(eX,eY,tf));
        }
    }

    public void die() {
        this.living=false;
    }
}
