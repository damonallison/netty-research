package com.damonallison.netty.client;

import com.damonallison.netty.utilities.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

	/**
	 * Runs the client. The client is will connect to localhost:10000.
	 *
	 * @param args
	 * @throws Exception
     */
	public static void main(String[] args) throws Exception {

		EchoClient c = new EchoClient("localhost", 10000);
		c.start();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		String line = null;
		do {
			Log.write("Enter something :");
			if (line != null) {
				c.writeMessage(line);
			}
		} while ((line = bufferedReader.readLine()) != null);
	}
}
