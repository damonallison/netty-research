package com.damonallison.netty.echoserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * The {@link ChannelInitializer} is called when a new channel is registered.
 *
 * This is where the pipeline is setup for the new channel.
 */
public class EchoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new EchoServerInboundHandler());
    }
}
