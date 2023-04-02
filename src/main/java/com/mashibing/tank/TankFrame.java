package com.mashibing.tank;

import com.mashibing.netty.Client;
import com.mashibing.netty.TankStartMovingMsg;
import com.mashibing.netty.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE=new TankFrame();

    Random r=new Random();

    Tank myTank=new Tank(r.nextInt(GAME_WIDTH),r.nextInt(GAME_HEIGHT),Dir.DOWN,Group.GOOD,this);
    public List<Bullet> bullets=new ArrayList<>();
    public Map<UUID,Tank> tanks=new HashMap<>();
    public List<Explode> explodes=new ArrayList<>();

    static final int GAME_WIDTH=1080,GAME_HEIGHT=960;

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    public void addTank(Tank t){
        tanks.put(t.getId(),t);
    }

    public Tank findTankByUUID(UUID id){
        return tanks.get(id);
    }

    public Bullet findBulletByUUID(UUID id) {
        for(int i=0; i<bullets.size(); i++){
            if(bullets.get(i).getId().equals(id)){
                return bullets.get(i);
            }
        }

        return null;
    }


    public TankFrame(){
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        //this.setVisible(true);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
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
        g.drawString("bullets:"+bullets.size(),10,60);
        g.drawString("tanks:"+tanks.size(),10,80);
        g.drawString("explodes:"+explodes.size(),10,100);
        g.setColor(color);

        myTank.paint(g);

        //初始化子弹
        for(int i=0 ;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }

        //java8 stream api
        tanks.values().stream().forEach((e)->e.paint(g));

        //爆炸展示
        for(int i=0;i<explodes.size();i++){
            explodes.get(i).paint(g);
        }

        //collision detect
        Collection<Tank> values=tanks.values();
        for(int i=0;i<bullets.size();i++){
            for (Tank t:values){
                bullets.get(i).collideWith(t);
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
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU=true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR=true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD=true;
                    setMainTankDir();
                    break;
                default:
                    break;
            }

           // new Thread(()->new Audio("audio/tank_move.wav").play()).start();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key=e.getKeyCode();

            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU=false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR=false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD=false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
        }

        private void setMainTankDir() {

            Dir dir=myTank.getDir();

            if(!bL && !bU && !bR && !bD){
                myTank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMainTank()));
            }else{

                if(bL)
                    myTank.setDir(Dir.LEFT);
                if(bU)
                    myTank.setDir(Dir.UP);
                if(bR)
                    myTank.setDir(Dir.RIGHT);
                if(bD)
                    myTank.setDir(Dir.DOWN);
                //发出坦克移动消息
                if(!myTank.isMoving())
                    Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));

                myTank.setMoving(true);

                if(dir !=myTank.getDir()){
                    //Client.INSTANCE.send(new TankDirChangedMsg(myTank));
                }
            }
        }
    }

    public Tank getMainTank(){
        return this.myTank;
    }
}
