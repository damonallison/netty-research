package com.damonallison.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import com.damonallison.netty.client.utilities.Log;

// Sharable == don't hold state on this class!
@Sharable
/** 
 * Extends {@link SimpleChannelInboundHandler} which will autorelease 
 * buffer objects for us. 
 * 
 * ChannelInboundHandlerAdapter is the abstract base class that will simply forward events to the 
 * next handler in the pipeline.
 */
public class EchoClientInboundHandler extends
		SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		Log.write("Channel active " + ctx);
	}

	/**
	 * This method name is bad. It's going to be renamed. Called for each
	 * message the handler receives.
	 * 
	 * Channel read will be called anytime we receive a message - it may not be
	 * a complete message - just a few bytes will trigger a read.
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
		System.out.println("Client received : "
				+ in.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
