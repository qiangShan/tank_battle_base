package com.mashibing.netty;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

public class TankJoinMsgDecoder extends ByteToMessageDecoder {
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

        switch (msgType){
            case TankJoin:
                TankJoinMsg msg=new TankJoinMsg();
                msg.parse(bytes);
                out.add(msg);
                break;
            default:
                break;
        }
    }
}
