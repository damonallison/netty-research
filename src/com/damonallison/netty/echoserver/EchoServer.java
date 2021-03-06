package com.damonallison.netty.echoserver;

import com.damonallison.netty.utilities.Log;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.ChannelHandler;

import java.net.InetSocketAddress;

/**
 * EchoServer has a single {@link ChannelHandler} that will simply echo back what it is sent.
 *
 * TODO: Split out the {@link ChannelInitializer} into a custom class.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        Log.write("EchoServer starting on port " + port);

        // NIO is the most widely used transport. (scalable and async)
        // OIO (old-io) could be used here - but you'd get a thread per channel - a complete waste.
        EventLoopGroup group = new NioEventLoopGroup(8);

        try {

            // A "bootstrap" is container for all netty configuration.
            // Netty provides a ServerBootstrap (for listening) and a Bootstrap for establishing connections.
            // A "ServerBootstrap" requires two EventLoopGroups:
            // 1. The first channel group contains a single "ServerSocket" channel.
            // 2. The second channel group contains all channels created to handle incoming connections.
            //
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new EchoServerChannelInitializer());

            Log.write("Binding port " + port);
            ChannelFuture f = b.bind().sync(); // bind the channel (blocking until it is bound).
            Log.write("EchoServer started on port " + port);
            f.channel().closeFuture().sync();  // block until the channel closes
            Log.write("EchoServer closed on port " + port);
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
