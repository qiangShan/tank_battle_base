package com.mashibing.tank;

import com.mashibing.netty.BulletNewMsg;
import com.mashibing.netty.Client;
import com.mashibing.netty.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank {

    private static final int SPEED=2;

    public static final int WIDTH=ResourceMgr.goodTankD.getWidth();

    public static final int HEIGHT=ResourceMgr.goodTankD.getHeight();

    private UUID id=UUID.randomUUID();

    Rectangle rect=new Rectangle();

    private Random random=new Random();

    private int x,y;

    private Dir dir=Dir.DOWN;

    private boolean moving=false;

    private TankFrame tf=null;

    private Group group=Group.BAD;

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

    public Tank(TankJoinMsg msg) {
        this.x= msg.x;
        this.y=msg.y;
        this.dir=msg.dir;
        this.moving=msg.moving;
        this.group=msg.group;
        this.id=msg.id;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;
    }

    //边界碰撞
    private void boundsCheck() {
        if(x<2) x=2;
        if(y<28) y=28;
        if(x>TankFrame.GAME_WIDTH-Tank.WIDTH-2) x=TankFrame.GAME_WIDTH-Tank.WIDTH-2;
        if(y>TankFrame.GAME_HEIGHT-Tank.HEIGHT-2) y=TankFrame.GAME_HEIGHT-Tank.HEIGHT-2;
    }

    public void die() {
        this.living=false;
        int eX=this.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
        int eY=this.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
        TankFrame.INSTANCE.explodes.add(new Explode(eX,eY));
    }

    //开火
    public void fire() {

        int bX=this.x+Tank.WIDTH/2-Bullet.WIDTH/2;
        int bY=this.y+Tank.HEIGHT/2-Bullet.HEIGHT/2;

        Bullet b=new Bullet(this.id, bX, bY,this.dir, this.group, this.tf);
        tf.bullets.add(b);

        Client.INSTANCE.send(new BulletNewMsg(b));

        if(this.group == Group.GOOD){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }

    private void move() {

        if(!living) return;

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

        if(this.group == Group.BAD && random.nextInt(100)>95)
            randomDir();

        boundsCheck();

        //update rect
        rect.x=this.x;
        rect.y=this.y;
    }

    public void paint(Graphics g) {
        //uuid on head
        Color color=g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), this.x, this.y-20);
        g.drawString("live="+living,x,y-10);
        g.setColor(color);

        //draw a rect if dead
        if(!living){

            moving=false;
            Color cc=g.getColor();
            g.setColor(Color.WHITE);
            g.drawRect(x,y,WIDTH,HEIGHT);
            g.setColor(cc);
            return;

        }

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

    //坦克随机方向
    private void randomDir() {
        this.dir=Dir.values()[random.nextInt(4)];
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }
}
