package com.montra.jtt808;


import com.montra.jtt808.core.handler.Jtt808MessageDecoder;
import com.montra.jtt808.core.handler.Jtt808MessageEncoder;
import com.montra.jtt808.core.handler.Jtt808ServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;  // This is the correct Netty class
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Jtt808ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        
        // Add frame decoder with proper JTT808 settings
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
            1024, 1, 2, -5, 0));
            
        // Add protocol handlers
        pipeline.addLast(new Jtt808MessageDecoder());
        pipeline.addLast(new Jtt808MessageEncoder());
        
        // Add business logic handler
        pipeline.addLast(new Jtt808ServerHandler());
        
        // Optional: Add logging handler
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
    }
}