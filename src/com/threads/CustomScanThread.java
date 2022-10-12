package com.threads;

public class CustomScanThread {
	private int TIMEOUT;
	private String host;
	private String[] result;

	public CustomScanThread(final String host, final int timeout) {
		this.TIMEOUT = timeout;
		this.host = host;
	}
}
