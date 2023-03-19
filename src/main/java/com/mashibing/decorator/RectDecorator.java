package com.mashibing.decorator;

import com.mashibing.mediator.GameObject;

import java.awt.*;

public class RectDecorator extends GODecorator{

    public RectDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g){

        this.x=go.getX();
        this.y=go.getY();

        go.paint(g);

        Color color=g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(go.getX(),go.getY(),go.getWidth()+20,go.getHeight()+20);
        g.setColor(color);
    }

    @Override
    public int getWidth() {
        return go.getWidth();
    }

    @Override
    public int getHeight() {
        return go.getHeight();
    }
}
