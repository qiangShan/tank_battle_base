package com.mashibing.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageToByteEncoderO<T> {

    protected abstract void encode(ChannelHandlerContext ctx, T t, ByteBuf buf);
}
