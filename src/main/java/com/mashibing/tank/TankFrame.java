package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    static final int GAME_WIDTH=960,GAME_HEIGHT=720;

    Tank myTank=new Tank(200,600,Dir.DOWN,Group.GOOD,this);
    public List<Bullet> bullets=new ArrayList<>();
    public List<Tank> tanks=new ArrayList<Tank>();
    public List<Explode> explodes=new ArrayList<Explode>();

    public TankFrame(){
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("tank_battle");
        this.setVisible(true);

        this.addKeyListener(new MyKeyListener());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    //解决游戏中的双闪问题
    Image offScreenImage=null;
    @Override
    public void update(Graphics graphics){
        if(offScreenImage == null){
            offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffSecreen = offScreenImage.getGraphics();
        Color color=gOffSecreen.getColor();
        gOffSecreen.setColor(Color.BLACK);
        gOffSecreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffSecreen.setColor(color);
        paint(gOffSecreen);
        graphics.drawImage(offScreenImage,0,0,null);
    }


    @Override
    public void paint(Graphics g){  // bjmashibing/tank

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

    class MyKeyListener extends KeyAdapter{

        boolean bL=false;
        boolean bU=false;
        boolean bR=false;
        boolean bD=false;

        @Override
        public void keyPressed(KeyEvent e) {

            int key=e.getKeyCode();

            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=true;
                    break;
                case KeyEvent.VK_UP:
                    bU=true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR=true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD=true;
                    break;
                default:
                    break;
            }

            setMainTankDir();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key=e.getKeyCode();

            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=false;
                    break;
                case KeyEvent.VK_UP:
                    bU=false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR=false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD=false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {

            if(!bL && !bU && !bR && !bD){
                myTank.setMoving(false);
            }else{
                myTank.setMoving(true);
                if(bL) myTank.setDir(Dir.LEFT);
                if(bU) myTank.setDir(Dir.UP);
                if(bR) myTank.setDir(Dir.RIGHT);
                if(bD) myTank.setDir(Dir.DOWN);
            }
        }
    }
}
