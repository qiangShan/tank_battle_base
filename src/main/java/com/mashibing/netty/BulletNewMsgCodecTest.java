package com.mashibing.netty;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BulletNewMsgCodecTest {

    @Test
    public void testEncode(){
        EmbeddedChannel ch=new EmbeddedChannel();

        UUID playerId=UUID.randomUUID();
        UUID id=UUID.randomUUID();
        BulletNewMsg msg=new BulletNewMsg(playerId, id, 5,10, Dir.DOWN, Group.BAD);
        ch.pipeline()
                .addLast(new MsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf=(ByteBuf) ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        assertEquals(MsgType.BulletNew,msgType);

        int length=buf.readInt();
        assertEquals(48,length);

        UUID playerID=new UUID(buf.readLong(),buf.readLong());
        UUID uuid=new UUID(buf.readLong(),buf.readLong());
        int x=buf.readInt();
        int y=buf.readInt();
        int dirOrdinal=buf.readInt();
        Dir dir=Dir.values()[dirOrdinal];
        int groupOrdinal=buf.readInt();
        Group g=Group.values()[groupOrdinal];

        assertEquals(playerId,playerID);
        assertEquals(id,uuid);
        assertEquals(5,x);
        assertEquals(10,y);
        assertEquals(Dir.DOWN,dir);
        assertEquals(Group.BAD,g);

    }

    @Test
    public void testDecoder(){

        EmbeddedChannel ch=new EmbeddedChannel();

        UUID playerID=UUID.randomUUID();
        UUID id=UUID.randomUUID();
        BulletNewMsg msg=new BulletNewMsg(playerID, id, 5,10, Dir.DOWN, Group.BAD);
        ch.pipeline()
                .addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        buf.writeInt(MsgType.BulletNew.ordinal());
        byte[] bytes=msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());

        BulletNewMsg msgR=(BulletNewMsg) ch.readInbound();

        assertEquals(playerID,msgR.playerID);
        assertEquals(id,msgR.id);
        assertEquals(5,msgR.x);
        assertEquals(10,msgR.y);
        assertEquals(Dir.DOWN,msgR.dir);
        assertEquals(Group.BAD,msgR.group);
    }
}
