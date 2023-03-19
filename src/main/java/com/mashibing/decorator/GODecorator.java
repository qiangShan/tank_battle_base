package com.mashibing.decorator;

import com.mashibing.mediator.GameObject;

import java.awt.*;

public abstract class GODecorator extends GameObject {

    GameObject go;

    public GODecorator(GameObject go){
        this.go=go;
    }

    @Override
    public abstract void paint(Graphics g) ;

}
