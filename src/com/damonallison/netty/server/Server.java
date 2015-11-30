package com.damonallison.netty.server;

import com.damonallison.netty.echoserver.EchoServer;
import io.netty.buffer.ByteBuf;

public class Server {

	/**
	 * Netty server bootstrap.
	 */
	public static void main(String[] args) throws Exception {

		EchoServer s = new EchoServer(10000);
		s.start();
	}

}
