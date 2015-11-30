package com.damonallison.netty.echoserver;

import com.damonallison.netty.utilities.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Simply echos back what it is given.
 * 
 * This handler is {@link Sharable}, so it must be thread safe and not maintain state.
 *
 * If your handler must maintain state, it must <em>not</em> be sharable.
 *
 * For example, if you want to keep track of per-channel read/write counts, or number of bytes transmitted,
 * use a non-sharable {@link ChannelHandler}.
 */
@Sharable
public class EchoServerInboundHandler extends ChannelInboundHandlerAdapter {

	// ---------------------------------------------------------------------
	// Lifecycle
	// ---------------------------------------------------------------------
	@Override 
	public void channelRegistered(ChannelHandlerContext ctx) {
		Log.write("Channel registered : " + ctx);
	}

	@Override 
	public void channelUnregistered(ChannelHandlerContext ctx) {
		Log.write("Channel unregistered : " + ctx);
	}

	@Override 
	public void channelActive(ChannelHandlerContext ctx) {
		Log.write("Channel active : " + ctx);
	}

	@Override 
	public void channelInactive(ChannelHandlerContext ctx) {
		Log.write("Channel inactive : " + ctx);
	}
	
	/**
	 * Called for each incoming message. Echos back what we are given
	 */
	@Override 	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf)msg;
		String payload = in.toString(CharsetUtil.UTF_8);
		Log.write("Server received \"" + payload + "\"");
		ctx.write(msg);
	}
	
	/** 
	 * Called to notify the handler that the last call made to channelRead() was the 
	 * last message in the current batch.
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ChannelFuture future = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
		future.addListener((f) -> Log.write("Channel Read complete. Channel flushed."));
	}
	
	/**
	 * Typically it is difficult to recover from connection errors - so simply closing 
	 * the channel is the right thing to do.
	 * 
	 * If *no* handlers in the pipeline catch an exception, a warning message is logged.
	 * You should always have a handler that catches an exception!
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Log.write("Exception caught : " + cause);
		cause.printStackTrace();

		ChannelFuture f = ctx.close();
		f.addListener((future) -> Log.write("Channel was closed!"));
	}
}
