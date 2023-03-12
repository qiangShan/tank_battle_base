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
        x += 10;
        y += 10;
    }

    class MyKeyListener extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
