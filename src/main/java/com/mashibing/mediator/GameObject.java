package com.mashibing.mediator;

import java.awt.*;

public abstract class GameObject {


    protected int x,y;

    public abstract void paint(Graphics g);

    public abstract int getWidth();

    public abstract int getHeight();

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
}
