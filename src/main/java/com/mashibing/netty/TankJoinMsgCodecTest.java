package com.mashibing.netty;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankJoinMsgCodecTest {

    @Test
    public void testEncoder(){
        EmbeddedChannel ec=new EmbeddedChannel();

        UUID id=UUID.randomUUID();
        TankJoinMsg tankJoinMsg=new TankJoinMsg(5,10, Dir.DOWN,true, Group.BAD,id);
        ec.pipeline()
                .addLast( new MsgEncoder());
        ec.writeOutbound(tankJoinMsg);

        ByteBuf buf=(ByteBuf) ec.readOutbound();
        int x=buf.readInt();
        int y=buf.readInt();
        int dirOrdinal=buf.readInt();
        Dir dir=Dir.values()[dirOrdinal];
        boolean moving=buf.readBoolean();
        int groupOrdinal=buf.readInt();
        Group group=Group.values()[groupOrdinal];
        UUID uuid=new UUID(buf.readLong(),buf.readLong());
        assertEquals(5,x);
        assertEquals(10,y);
        assertEquals(Dir.DOWN,dir);
        assertEquals(true,moving);
        assertEquals(Group.BAD,group);
        assertEquals(id,uuid);
    }

    @Test
    public void testDecoder(){

        EmbeddedChannel ec=new EmbeddedChannel();

        UUID id=UUID.randomUUID();
        TankJoinMsg tankJoinMsg=new TankJoinMsg(5,10,Dir.DOWN,true,Group.BAD,id);
        ec.pipeline()
                .addLast( new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        buf.writeBytes(tankJoinMsg.toBytes());

        ec.writeInbound(buf.duplicate());
        TankJoinMsg tankJoinMsgR=(TankJoinMsg) ec.readInbound();

        assertEquals(5,tankJoinMsgR.x);
        assertEquals(10,tankJoinMsgR.y);
        assertEquals(Dir.DOWN,tankJoinMsgR.dir);
        assertEquals(true,tankJoinMsgR.moving);
        assertEquals(Group.BAD,tankJoinMsgR.group);
        assertEquals(id,tankJoinMsgR.id);
    }
}
