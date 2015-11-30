package com.damonallison.netty.client;

import com.damonallison.netty.utilities.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class MessageCounterOutboundHandler extends ChannelOutboundHandlerAdapter {

	private int count = 0;
	private String name;

	public MessageCounterOutboundHandler(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		Log.write("outbound writing : " + this.name);
		++count;
		ctx.flush();
	}

}
