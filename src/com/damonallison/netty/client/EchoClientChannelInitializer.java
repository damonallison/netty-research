package com.damonallison.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import com.damonallison.netty.utilities.Log;

public class EchoClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        Log.write("Channel Initialized : " + ch);

        // The "first" in an outbound pipeline is actually the
        // last handler to be fired.
        ch.pipeline().addFirst(
                new MessageCounterOutboundHandler("1"));
        ch.pipeline().addFirst(
                new MessageCounterOutboundHandler("2"));
        ch.pipeline().addLast(new EchoClientInboundHandler());
    }
}
