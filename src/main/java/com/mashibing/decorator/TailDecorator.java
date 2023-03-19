package com.mashibing.decorator;

import com.mashibing.mediator.GameObject;

import java.awt.*;

public class TailDecorator extends GODecorator{

    public TailDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g){

        this.x=go.getX();
        this.y=go.getY();

        go.paint(g);

        Color color=g.getColor();
        g.setColor(Color.YELLOW);
        g.drawLine(go.getX(),go.getY(),go.getX() +go.getWidth(),go .getY()+go.getHeight());
        g.setColor(color);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
