package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    int x=200,y=200;

    public TankFrame(){
        this.setSize(960,720);
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

    @Override
    public void paint(Graphics g){
        g.fillRect(x,y,50,50);
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
                default:
                    break;
            }
        }
    }
}
