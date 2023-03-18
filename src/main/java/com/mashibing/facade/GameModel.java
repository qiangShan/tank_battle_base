package com.mashibing.facade;

import com.mashibing.cor.BulletTankCollider;
import com.mashibing.cor.Collider;
import com.mashibing.cor.ColliderChain;
import com.mashibing.cor.TankTankCollider;
import com.mashibing.mediator.GameObject;
import com.mashibing.tank.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank=new Tank(200,600, Dir.DOWN, Group.GOOD,this);


    ColliderChain chain=new ColliderChain();

    private List<GameObject> objects=new ArrayList<GameObject>();

    public GameModel(){
        int initTankCount =Integer.parseInt((String)PropertyMgr.get("initTankCount")) ;
        //初始化敌方坦克
        for(int i=0;i<initTankCount;i++){
            add(new Tank(50+i*80,200, Dir.DOWN, Group.BAD,this));
        }
    }

    public void add(GameObject go){
        this.objects.add(go);
    }

    public void remove(GameObject go){
        this.objects.remove(go);
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        //g.setColor(Color.WHITE);
        //g.drawString("子弹的数量:"+bullets.size(),10,60);
        //g.drawString("敌人坦克的数量:"+tanks.size(),10,80);
        //g.drawString("爆炸的数量:"+explodes.size(),10,100);
        g.setColor(color);

        myTank.paint(g);

        //初始化子弹
        for(int i=0 ;i<objects.size();i++){
            objects.get(i).paint(g);
        }

        //互相碰撞
        for(int i=0;i<objects.size();i++){
            for(int j=i+1;j<objects.size();j++){
                GameObject o1=objects.get(i);
                GameObject o2=objects.get(j);
                //for
                chain.collide(o1,o2);
            }
        }


        /**
         * 碰撞检测，判断子弹是否与坦克相撞
         * for(int i=0;i<bullets.size();i++){
         *             for(int j=0;j<tanks.size();j++){
         *                 bullets.get(i).collideWith(tanks.get(j));
         *             }
         *         }
         */
    }

    public Tank getMainTank() {

        return myTank;
    }
}
