package com.pref;

public class Preference {
	public int PORT_TIMEOUT;
	public int IP_TIMEOUT;
	public int PORT_THREADS;
	public int IP_THREADS;

	public int getPORT_TIMEOUT() {
		return PORT_TIMEOUT;
	}

	public int getIP_TIMEOUT() {
		return IP_TIMEOUT;
	}

	public int getPORT_THREADS() {
		return PORT_THREADS;
	}

	public int getIP_THREADS() {
		return IP_THREADS;
	}

	public void setPORT_TIMEOUT(int pORT_TIMEOUT) {
		PORT_TIMEOUT = pORT_TIMEOUT;
	}

	public void setIP_TIMEOUT(int iP_TIMEOUT) {
		IP_TIMEOUT = iP_TIMEOUT;
	}

	public void setPORT_THREADS(int pORT_THREADS) {
		PORT_THREADS = pORT_THREADS;
	}

	public void setIP_THREADS(int iP_THREADS) {
		IP_THREADS = iP_THREADS;
	}

	@Override
	public String toString() {
		return "Preference [PORT_TIMEOUT=" + PORT_TIMEOUT + ", IP_TIMEOUT=" + IP_TIMEOUT + ", PORT_THREADS="
				+ PORT_THREADS + ", IP_THREADS=" + IP_THREADS + "]";
	}

}
