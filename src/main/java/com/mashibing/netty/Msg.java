package com.mashibing.netty;

public abstract class Msg {
    public abstract void handle();
    public abstract byte[] toBytes();
}
