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

        if(in.readableBytes()<33){  //TCP拆包和粘包问题
            return;
        }
        TankJoinMsg tankJoinMsg=new TankJoinMsg();
        tankJoinMsg.x=in.readInt();
        tankJoinMsg.y=in.readInt();
        tankJoinMsg.dir= Dir.values()[in.readInt()];
        tankJoinMsg.moving=in.readBoolean();
        tankJoinMsg.group= Group.values()[in.readInt()];
        tankJoinMsg.id=new UUID(in.readLong(),in.readLong());

        out.add(tankJoinMsg);
    }
}
