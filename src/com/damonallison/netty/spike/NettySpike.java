package com.damonallison.netty.spike;

import com.damonallison.netty.echoserver.EchoServer;

public class NettySpike {

	/**
	 * Netty server bootstrap.
	 */
	public static void main(String[] args) throws Exception {

		EchoServer s = new EchoServer(10000);
		s.start();
		
	}
}
