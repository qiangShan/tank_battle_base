package com.mashibing.netty;

import com.mashibing.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankDieMsgCodecTest {



    @Test
    public void testEncode(){
        EmbeddedChannel ch=new EmbeddedChannel();

        UUID bulletId=UUID.randomUUID();
        UUID id=UUID.randomUUID();
        TankDieMsg msg=new TankDieMsg(bulletId,id);
        ch.pipeline()
                .addLast(new MsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf=(ByteBuf) ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankDie,msgType);

        int length=buf.readInt();
        assertEquals(32,length);

        UUID playerId=new UUID(buf.readLong(),buf.readLong());
        UUID uuid=new UUID(buf.readLong(),buf.readLong());

        assertEquals(bulletId,playerId);
        assertEquals(id,uuid);
    }

    @Test
    public void testDecoder(){

        EmbeddedChannel ch=new EmbeddedChannel();

        UUID bulletId=UUID.randomUUID();
        UUID id=UUID.randomUUID();
        TankDieMsg msg=new TankDieMsg(bulletId,id);
        ch.pipeline()
                .addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        buf.writeInt(MsgType.TankDie.ordinal());
        byte[] bytes=msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());

        TankDieMsg msgR=(TankDieMsg) ch.readInbound();

        assertEquals(bulletId,msgR.bulletId);
        assertEquals(id,msgR.id);

    }
}
