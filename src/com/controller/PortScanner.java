package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.pref.Persist;
import com.pref.Preference;
import com.threads.PortListener;
import com.threads.PortScannerThread;
import com.util.ScanUtil;

public class PortScanner {
	private static List<Future<Integer[]>> portlist;
	private static ThreadPoolExecutor executor;

	static {
		PortScanner.portlist = new ArrayList<Future<Integer[]>>();

	}

	public static void scan(final List<Integer> ports, final String ip, final PortListener listener) {Preference pr = null;try {
			pr = Persist.loadPreferences();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PortScanner.portlist.clear();
		PortScanner.executor = ScanUtil.createExecutor(pr.getPORT_THREADS());
		for (final int p : ports) {
			final Callable<Integer[]> portcall = new PortScannerThread(ip, p, pr.getPORT_TIMEOUT());
			final Future<Integer[]> f = PortScanner.executor.submit(portcall);
			PortScanner.portlist.add(f);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < PortScanner.portlist.size(); ++i) {
					try {
						if (PortScanner.portlist.get(i).get()[1] == 1) {
							listener.onOpen(PortScanner.portlist.get(i).get()[0], i);
						} else {
							listener.onClose(PortScanner.portlist.get(i).get()[0], i);
						}
					} catch (InterruptedException | CancellationException | ExecutionException ex) {
						final Exception e = ex;
						System.out.println(e.getMessage());
						Thread.currentThread().interrupt();
						PortScanner.portlist.clear();
					}
					if (i == PortScanner.portlist.size() - 1) {
						listener.isComplete(true);
					} else {
						listener.isComplete(false);
					}
				}
			}
		}).start();
		PortScanner.executor.shutdown();
		System.out.println("All Request Completed");
	}

	public static void stop() {
		try {
			if (!PortScanner.portlist.isEmpty()) {
				System.out.println("Stopping");
				for (final Future<Integer[]> f : PortScanner.portlist) {
					f.cancel(false);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println(">>>" + e.getMessage());
		}
		PortScanner.portlist.clear();
	}
}
