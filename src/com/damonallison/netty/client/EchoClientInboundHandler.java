package com.damonallison.netty.client;

import com.damonallison.netty.utilities.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Extends {@link SimpleChannelInboundHandler} which will autorelease 
 * buffer objects for us. 
 * 
 * ChannelInboundHandlerAdapter is the abstract base class that will simply forward events to the 
 * next handler in the pipeline.
 */
public class EchoClientInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

	private StringBuilder currentMessage = new StringBuilder();

	protected EchoClientInboundHandler() {
		super(false);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Log.write("channelRegistered: " + ctx.channel());
		ctx.fireChannelRegistered();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		Log.write("channelUnregistered: " + ctx.channel());
		ctx.fireChannelUnregistered();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Log.write("Channel active " + ctx.channel());
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Log.write("Channel inactive " + ctx.channel());
		ctx.fireChannelInactive();
	}

	/**
	 * This method name is bad. It's going to be renamed. Called for each
	 * message the handler receives.
	 * 
	 * Channel read will be called anytime we receive a message - it may not be
	 * a complete message - just a few bytes will trigger a read.
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

		currentMessage.append(in.toString(CharsetUtil.UTF_8));

		// We can't .fireChannelRead(in) here because in refCnt will be 0. We could increase the refCnt
		// to forward it on if we really wanted to.
		ReferenceCountUtil.retain(in);

		ctx.fireChannelRead(in);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		Log.write("Client received: " + currentMessage.toString());
		currentMessage = new StringBuilder();
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
