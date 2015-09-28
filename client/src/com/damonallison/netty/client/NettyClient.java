package com.damonallison.netty.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NettyClient {

	public static void main(String[] args) throws Exception {

		// if (args.length != 2) {
		// System.err.println("Usage : " + EchoClient.class.getSimpleName() +
		// "<host> <port>");
		// return;
		// }
		EchoClient c = new EchoClient("localhost", 10000);
		c.start();

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));

		String line = null;
		do {
			System.out.println("Enter something :");
			if (line != null) {
				c.writeMessage(line);
			}
		} while ((line = bufferedReader.readLine()) != null);
	}
}
