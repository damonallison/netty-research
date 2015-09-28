package com.damonallison.netty.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import com.damonallison.netty.spike.Log;

public class EchoServer {

	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public void start() throws Exception {
		Log.write("EchoServer starting on port " + port);

		// NIO is the most widely used transport. (scalable and async)
		// OIO (old-io) could be used here - but you'd get a thread per channel - a complete waste.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			
			// A "bootstrap" is container for all netty configuration.
			// Netty provides a ServerBootstrap (for listening) and a Bootstrap for establishing connections.
			// A "ServerBootstrap" requires two EventLoopGroups - one for each channel group.
			// 1. The first channel group contains a single "ServerSocket" channel.
			// 2. The second channel group contains all channels created to handle incoming connections.
			//
			ServerBootstrap b = new ServerBootstrap();
			b.group(group) //
			 .channel(NioServerSocketChannel.class) //
			 .localAddress(new InetSocketAddress(port)) //
			 .childHandler(new ChannelInitializer<SocketChannel>() {
				 /**
				  * Called when a new channel is created. This is where we setup our pipeline on the new channel.
				  */
				 @Override
				 public void initChannel(SocketChannel ch) throws Exception {
					 Log.write("EchoServer initializing a new channel " + ch);
					 ch.pipeline().addLast(new EchoServerInboundHandler());
				 }
			 });
			Log.write("Binding port " + port);
			ChannelFuture f = b.bind().sync(); // bind the channel
			Log.write("EchoServer started on port " + port);
			f.channel().closeFuture().sync();  // block until the channel closes
			Log.write("EchoServer closed on port " + port);
		}
		finally {
			group.shutdownGracefully().sync();
		}
	}
}
