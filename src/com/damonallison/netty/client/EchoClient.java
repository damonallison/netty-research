package com.damonallison.netty.client;

import com.damonallison.netty.utilities.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Promise;

import java.net.InetSocketAddress;

public class EchoClient {

    private final String host;
    private final int port;

    private EventLoopGroup group;
    private Channel channel;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        Log.write("Starting echo client to " + host + ":" + port);
        this.group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();


        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new EchoClientChannelInitializer());

        ChannelFuture f = b.connect().sync(); // Block until connection is successful.
        this.channel = f.channel();

        Log.write("EchoClient connected to " + host + ":" + port);

        f.channel().closeFuture().addListener(future -> {
            Log.write("EchoClient channel closed to " + host + ":" + port);
        });

        // f.channel().closeFuture().sync(); // Block until the channel
        // closes.
    }

    public void writeMessage(String message) {
        if (channel.isActive()) {

            channel.writeAndFlush(
                    Unpooled.copiedBuffer(message, CharsetUtil.UTF_8))
                    .addListener(future -> {
                        if (!future.isSuccess()) {
                            Log.write("error writing to channel ");
                            future.cause().printStackTrace();
                            channel.close();
                        }
                    })
                    .addListener(
                            future -> {
                                Log.write("Wrote message : " + message);
                                MessageCounterOutboundHandler counter = channel
                                        .pipeline()
                                        .get(MessageCounterOutboundHandler.class);
                                Log.write("Wrote a total of "
                                        + counter.getCount() + " messages");
                            });
        } else {
            Log.write("Message not sent - channel is closed");
        }
    }

    public void stop() throws Exception {
        group.shutdownGracefully().addListener(future -> {
            Log.write("Echo client stopped");
        });
    }
}
