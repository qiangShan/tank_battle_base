package com.mashibing.facade;

import com.mashibing.tank.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank=new Tank(200,600, Dir.DOWN, Group.GOOD,this);
    public List<Bullet> bullets=new ArrayList<>();
    public List<Tank> tanks=new ArrayList<Tank>();
    public List<Explode> explodes=new ArrayList<Explode>();



    public GameModel(){
        int initTankCount =Integer.parseInt((String)PropertyMgr.get("initTankCount")) ;
        //初始化敌方坦克
        for(int i=0;i<initTankCount;i++){
            tanks.add(new Tank(50+i*80,200, Dir.DOWN, Group.BAD,this));
        }

    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量:"+bullets.size(),10,60);
        g.drawString("敌人坦克的数量:"+tanks.size(),10,80);
        g.drawString("爆炸的数量:"+explodes.size(),10,100);
        g.setColor(color);

        myTank.paint(g);

        //初始化子弹
        for(int i=0 ;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }

        //画敌人坦克
        for(int i=0 ;i<tanks.size();i++){
            tanks.get(i).paint(g);
        }

        //爆炸展示
        for(int i=0;i<explodes.size();i++){
            explodes.get(i).paint(g);
        }

        //碰撞检测，判断子弹是否与坦克相撞
        for(int i=0;i<bullets.size();i++){
            for(int j=0;j<tanks.size();j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
    }

    public Tank getMainTank() {

        return myTank;
    }
}
