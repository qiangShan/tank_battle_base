package com.mashibing.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    public static ChannelGroup clients =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart(){
        EventLoopGroup boosGroup=new NioEventLoopGroup(2);
        EventLoopGroup workGroup=new NioEventLoopGroup(2);

        ServerBootstrap b=new ServerBootstrap();
        try {
            ChannelFuture f=b.group(boosGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //System.out.println(Thread.currentThread().getId());
                            ChannelPipeline pl = channel.pipeline();
                            pl.addLast(new MsgEncoder());
                            pl.addLast(new MsgDecoder());
                            pl.addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server started!");
            f.channel().closeFuture().sync();   //close() ->ChannelFuture

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter{  //SimpleChannelInboundHandler


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg) throws Exception{

        ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        Server.clients.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //判断出现异常的客户端channel，并关闭连接
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}


