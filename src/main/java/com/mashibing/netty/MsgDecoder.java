package com.mashibing.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) {

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

        switch (msgType){
            case TankJoin:
                msg=new TankJoinMsg();
                break;
            case TankStartMoving:
                msg=new TankStartMovingMsg();
                break;
            default:
                break;
        }

        msg.parse(bytes);
        out.add(msg);
    }
}
