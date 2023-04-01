package com.mashibing.netty;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankDirChangedMsgCodecTest {



    @Test
    public void testEncode(){
        EmbeddedChannel ch=new EmbeddedChannel();

        UUID id=UUID.randomUUID();
        TankDirChangedMsg msg=new TankDirChangedMsg(id,5,10,Dir.RIGHT);
        ch.pipeline()
                .addLast(new MsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf=(ByteBuf) ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankDirChanged,msgType);

        int length=buf.readInt();
        assertEquals(28,length);

        UUID uuid=new UUID(buf.readLong(),buf.readLong());
        int x=buf.readInt();
        int y=buf.readInt();
        int dirOrdinal=buf.readInt();
        Dir dir=Dir.values()[dirOrdinal];


        assertEquals(5,x);
        assertEquals(10,y);
        assertEquals(Dir.RIGHT,dir);
        assertEquals(id,uuid);
    }

    @Test
    public void testDecoder(){

        EmbeddedChannel ch=new EmbeddedChannel();

        UUID id=UUID.randomUUID();
        TankDirChangedMsg msg=new TankDirChangedMsg(id,5,10,Dir.RIGHT);
        ch.pipeline()
                .addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        buf.writeInt(MsgType.TankDirChanged.ordinal());
        byte[] bytes=msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());

        TankDirChangedMsg msgR=(TankDirChangedMsg) ch.readInbound();

        assertEquals(5,msgR.x);
        assertEquals(10,msgR.y);
        assertEquals(Dir.RIGHT,msgR.dir);
        assertEquals(id,msgR.id);

    }
}
