package com.mashibing.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {

        if(in.readableBytes()<8){  //TCP拆包和粘包问题
            return;
        }

        in.markReaderIndex();

        MsgType msgType=MsgType.values()[in.readInt()];
        int length=in.readInt();

        if(in.readableBytes()<length){
            in.resetReaderIndex();
            return;
        }

        byte[] bytes=new byte[length];
        in.readBytes(bytes);

        Msg msg=null;

        //reflection
        msg=(Msg) Class.forName("com.mashibing.netty."+msgType.toString()+"Msg").getDeclaredConstructor().newInstance();


        /**reflection
        //Class.forName(msgType.toString+"msg").constructor.newInstance();
        switch (msgType){
            case TankJoin:
                msg=new TankJoinMsg();
                break;
            case TankStartMoving:
                msg=new TankStartMovingMsg();
                break;
            case TankStop:
                msg=new TankStopMsg();
                break;
            default:
                break;
        }
       */

        msg.parse(bytes);
        out.add(msg);
    }
}
