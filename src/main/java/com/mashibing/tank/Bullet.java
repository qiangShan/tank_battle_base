package com.mashibing.tank;

import com.mashibing.facade.GameModel;
import com.mashibing.mediator.GameObject;

import java.awt.*;

public class Bullet extends GameObject {

    private static final int SPEED=10;
    public static final int WIDTH=ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT=ResourceMgr.bulletD.getHeight();

    private Dir dir;
    private Group group=Group.BAD;
    public Rectangle rect=new Rectangle();


    private boolean living =true;

    public Bullet(int x, int y, Dir dir ,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;

        GameModel.getInstance().add(this);

    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
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

    @Override
    public void paint(Graphics g) {

        if(!living){
            GameModel.getInstance().remove(this);
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

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
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

    /**
    public boolean collideWith(Tank tank) {
        if(this.group == tank.getGroup())  return false;

        if(rect.intersects(tank.rect)){
            tank.die();
            this.die();
            int eX=tank.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
            int eY=tank.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
            gm.add(new Explode(eX,eY,gm));
            return true;
        }

        return  false;
    }
     */

    public void die() {
        this.living=false;
    }
}
