package com.controller;

import java.util.List;

import com.threads.IPListener;
import com.threads.PortListener;
import com.threads.ScannerException;
import com.util.ScanUtil;

public class ScanController {
	private String fromIP;
	private String toIP;
	private int totalhost;
	private int totalports;
	private String portRange;
	private String IpOfPort;

	public int getTotalhost() {
		return this.totalhost;
	}

	public int getTotalPorts() {
		return this.totalports;
	}

	public void setIPRange(final String from, final String to) throws ScannerException {
		this.fromIP = from;
		this.toIP = to;
		this.totalhost = ScanUtil.getIPList(from, to).size();
	}

	public void setPortRange(final String IpOfPort, final String portRange) throws ScannerException {
		this.IpOfPort = IpOfPort;
		this.portRange = portRange;
		this.totalports = ScanUtil.getPortList(portRange).size();
	}

	public void scanIP(final IPListener listener) throws ScannerException {
		final List<String> iplist = ScanUtil.getIPList(this.fromIP, this.toIP);
		IPScanner.scan(iplist, listener);
	}

	public void scanPort(final PortListener listener) throws ScannerException {
		final List<Integer> ports = ScanUtil.getPortList(this.portRange);
		PortScanner.scan(ports, this.IpOfPort, listener);
	}

	public void scanIpWithCustomPort(final IPListener listener) {

	}
}
