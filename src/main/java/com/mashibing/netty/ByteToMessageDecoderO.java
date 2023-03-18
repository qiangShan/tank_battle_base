package com.mashibing.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public abstract class ByteToMessageDecoderO {

    protected abstract void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out);
}
