package com.threads;

import java.util.concurrent.*;

import com.util.ScannerException;

import java.io.*;
import java.net.*;

public class PortScannerThread implements Callable<Integer[]> {
	private final int TIMEOUT = 1000;
	private String host;
	private int port;
	private Integer[] result;

	public PortScannerThread(final String host, final int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public Integer[] call() throws Exception {
		(this.result = new Integer[2])[0] = this.port;
		try {
			final InetAddress address = InetAddress.getByName(this.host);
			final Socket s = new Socket();
			final SocketAddress socketAddress = new InetSocketAddress(address, this.port);
			s.connect(socketAddress, 1000);
			this.result[1] = 1;
			s.close();
			return this.result;
		} catch (UnknownHostException e) {
			throw new ScannerException(e.getMessage());
		} catch (IOException e2) {
			this.result[1] = 0;
			return this.result;
		}
	}
}
