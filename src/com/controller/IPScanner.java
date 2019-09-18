package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.pref.Persist;
import com.pref.Preference;
import com.threads.IPListener;
import com.threads.IPScannerThread;
import com.util.ScanUtil;

public class IPScanner {
	private static ThreadPoolExecutor executor;
	private static List<Future<String[]>> flist;

	static {
		IPScanner.flist = new ArrayList<Future<String[]>>();
	}

	public static void scan(final List<String> iplist, final IPListener listener) {
		Preference pr = null;
		try {
			pr = Persist.loadPreferences();
		} catch (IOException e) {
			e.printStackTrace();
		}
		IPScanner.flist.clear();
		IPScanner.executor = ScanUtil.createExecutor(pr.IP_THREADS);
		for (final String ip : iplist) {
			final Callable<String[]> ipcall = new IPScannerThread(ip, pr.IP_TIMEOUT);
			final Future<String[]> f = IPScanner.executor.submit(ipcall);
			IPScanner.flist.add(f);
		}
		System.out.println(IPScanner.flist.size());
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < IPScanner.flist.size(); ++i) {
					try {
						if (Boolean.parseBoolean(IPScanner.flist.get(i).get()[1])) {
							listener.onAlive(IPScanner.flist.get(i).get()[0], i);
						} else {
							listener.onSleep(IPScanner.flist.get(i).get()[0], i);
						}
					} catch (InterruptedException | ExecutionException ex) {
						System.out.println(ex.getMessage());
						Thread.currentThread().interrupt();
						IPScanner.flist.clear();
					}
					if (i == IPScanner.flist.size() - 1) {
						listener.isComplete(true);
					} else {
						listener.isComplete(false);
					}
				}
			}
		}).start();
		IPScanner.executor.shutdown();
		System.out.println("All Request Completed");
	}

	public static void customScan(final List<String> iplist, final int port, final IPListener listener) {

	}

	public static void stop() {
		System.out.println("Stopping");
		for (final Future<String[]> f : IPScanner.flist) {
			f.cancel(false);
		}
		IPScanner.flist.clear();
	}
}
