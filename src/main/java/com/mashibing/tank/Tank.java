package com.mashibing.tank;

import com.mashibing.observer.TankFireEvent;
import com.mashibing.observer.TankFireHandler;
import com.mashibing.observer.TankFireObserver;

import java.awt.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tank {

    private static final int SPEED=5;
    public static final int WIDTH=ResourceMgr.goodTankD.getWidth();
    public static final int HEIGHT=ResourceMgr.goodTankD.getHeight();

    private int x,y;
    private Dir dir=Dir.DOWN;
    private TankFrame tf=null;
    Rectangle rect=new Rectangle();

    private Random random=new Random();
    private Group group=Group.BAD;

    private boolean moving=true;
    private boolean living=true;

    public Tank(int x, int y, Dir dir ,Group group ,TankFrame tf) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public void paint(Graphics g) {

        if(!living) tf.tanks.remove(this);

        switch (dir){
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL ,x ,y,null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU ,x ,y,null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR ,x ,y,null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD ,x ,y,null);
                break;
            default:
                break;
        }

        move();

    }

    private void move() {

        if(!moving) return;
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

        if(this.group == Group.BAD && random.nextInt(100)>95)
            this.fire();

        if(this.group == Group.BAD)
            randomDir();

        boundsCheck();

        //update rect
        rect.x=this.x;
        rect.y=this.y;
    }

    //边界碰撞
    private void boundsCheck() {
       if(x<2) x=2;
       if(y<28) y=28;
       if(x>TankFrame.GAME_WIDTH-Tank.WIDTH-2) x=TankFrame.GAME_WIDTH-Tank.WIDTH-2;
       if(y>TankFrame.GAME_HEIGHT-Tank.HEIGHT-2) y=TankFrame.GAME_HEIGHT-Tank.HEIGHT-2;
    }

    //坦克随机方向
    private void randomDir() {
        if(this.group == Group.BAD && random.nextInt(100)>95)
            this.dir=Dir.values()[random.nextInt(4)];
    }

    //开火
    public void fire() {

        int bX=this.x+Tank.WIDTH/2-Bullet.WIDTH/2;
        int bY=this.y+Tank.HEIGHT/2-Bullet.HEIGHT/2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.group ,this.tf));
    }


    public void die() {
        this.living=false;
    }

    private List<TankFireObserver> fireObservers=Arrays.asList(new TankFireHandler());

    public void handleFireKey() {
        TankFireEvent event=new TankFireEvent(this);
        for (TankFireObserver o: fireObservers){
            o.actionOnFire(event);
        }
    }
}
