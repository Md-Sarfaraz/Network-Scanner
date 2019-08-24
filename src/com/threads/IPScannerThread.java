package com.threads;

import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class IPScannerThread implements Callable<String[]> {
	private final int TIMEOUT = 1000;
	private String host;
	private String[] result;

	public IPScannerThread(final String host) {
		this.host = host;
	}

	@Override
	public String[] call() throws Exception {
		this.result = new String[2];
		try {
			final InetAddress address = InetAddress.getByName(this.host);
			final boolean ping = address.isReachable(1000);
			this.result[0] = this.host;
			if (ping) {
				this.result[1] = "true";
				return this.result;
			}
			this.result[1] = "false";
			return this.result;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}
}
