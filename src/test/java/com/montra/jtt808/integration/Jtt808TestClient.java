package com.montra.jtt808.integration;

import com.montra.jtt808.core.Jtt808Message;
import com.montra.jtt808.core.handler.Jtt808MessageEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Jtt808TestClient {
    private Channel channel;
    
    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .handler(new ChannelInitializer<SocketChannel>() {
             @Override
             protected void initChannel(SocketChannel ch) {
                 ch.pipeline().addLast(new Jtt808MessageEncoder(), new Jtt808MessageEncoder());
             }
         });
        
        ChannelFuture f = b.connect(host, port).sync();
        channel = f.channel();
    }
    
    public void sendMessage(Jtt808Message msg) {
        channel.writeAndFlush(msg);
    }
    
    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}