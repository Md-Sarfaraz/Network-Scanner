package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.threads.ScannerException;

public class ScanUtil {
	private static Pattern pattern;
	private static Matcher matcher;

	public static List<String> getIPList(final String from, final String to) throws ScannerException {
		validateIP(from);
		final List<String> iplist = new ArrayList<String>();
		if (from.trim().isBlank() || to.trim().isBlank()) {
			return iplist;
		}
		if (validateIP(from) && validateIP(to)) {
			final int[] t1 = ipStrToINT(from);
			final int t2 = ipStrToINT(to)[3];
			final String sn = String.valueOf(t1[0]) + "." + t1[1] + "." + t1[2] + ".";
			for (int i = t1[3]; i <= t2; ++i) {
				iplist.add(String.valueOf(sn) + i);
			}
			System.out.println("Returning IP List");
			return iplist;
		}
		throw new ScannerException("Invalid IP Address\nCheck Again ...");
	}

	private static int[] ipStrToINT(final String ip) throws ScannerException {
		if (ip.isBlank()) {
			throw new ScannerException("IP Address is Blank");
		}
		if (!validateIP(ip)) {
			throw new ScannerException("IP Address is Invalid");
		}
		final int[] numIP = new int[4];
		final String[] tempIp = ip.split("\\.");
		for (int i = 0; i < tempIp.length; ++i) {
			numIP[i] = Integer.parseInt(tempIp[i]);
		}
		return numIP;
	}

	public static boolean validateIP(final String ip) {
		final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		ScanUtil.pattern = Pattern.compile(IPADDRESS_PATTERN);
		ScanUtil.matcher = ScanUtil.pattern.matcher(ip);
		return ScanUtil.matcher.matches();
	}

	private static List<Integer> getPortList(final int from, final int to) {
		final List<Integer> port = new ArrayList<Integer>();
		if (from == to) {
			port.add(from);
		} else {
			if (from > to) {
				return port;
			}
			if (from < to) {
				for (int i = from; i <= to; ++i) {
					port.add(i);
				}
			}
		}
		return port;
	}

	public static List<Integer> getPortList(final String range) throws ScannerException {
		if (range.isBlank()) {
			throw new ScannerException("Port Range Required");
		}
		if (range.contains(",")) {
			String[] sp = range.split(",");
			final Integer[] p = new Integer[sp.length];
			for (int i = 0; i < p.length; i++) {
				p[i] = Integer.parseInt(sp[i]);
			}
		}
		if (!range.contains("-")) {
			final int r = Integer.parseInt(range);
			final List<Integer> li = new ArrayList<Integer>();
			li.add(r);
			return li;
		}

		final String[] port = range.split("-");
		int from, to;
		try {
			from = Integer.parseInt(port[0]);
			to = Integer.parseInt(port[1]);
		} catch (NumberFormatException e) {
			throw new ScannerException("Port Range Wronge : " + range);
		}
		if (from > to) {
			throw new ScannerException("Port Range Wronge : " + range);
		}
		return getPortList(from, to);
	}

	public static ThreadPoolExecutor createExecutor(final int core, final int max) {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(core, max, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
					final AtomicInteger threadNumber = new AtomicInteger(1);

					@Override
					public Thread newThread(final Runnable r) {
						return new Thread(r, "Thread No : " + this.threadNumber.getAndIncrement());
					}
				});
		return executor;
	}
}
