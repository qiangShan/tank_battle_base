package com.mashibing.netty;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg{

    UUID id;
    int x,y;

    public TankStopMsg(){

    }

    public TankStopMsg(Tank t){
        this.id=t.getId();
        this.x=t.getX();
        this.y=t.getY();
    }

    public TankStopMsg(UUID id,int x, int y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    @Override
    public void handle() {

        //if this msg by myself do nothing
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;

        Tank t=TankFrame.INSTANCE.finByUUID(this.id);
        if(t != null ){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try{

            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();

            bytes=baos.toByteArray();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(dos != null){
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis=new DataInputStream(new ByteArrayInputStream(bytes));

        try{

            this.id=new UUID(dis.readLong(),dis.readLong());
            this.x=dis.readInt();
            this.y=dis.readInt();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
