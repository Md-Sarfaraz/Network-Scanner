package com.testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.util.ScanUtil;

public class Testing {
	public static void main(String... strings) {
		System.out.println(searchPort(443));
		fieldcheck();
	}

	public static String searchPort(int port) {
		try (BufferedReader br = new BufferedReader(new FileReader("src/com/pref/ports.csv"));) {
			String line;
			String sp = String.valueOf(port);
			while ((line = br.readLine()) != null) {
				String[] pname = line.split(",");
				if (pname[1].equalsIgnoreCase(sp)) {
					return pname[0].toUpperCase();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "UNKNOWN";
	}

	public static void fieldcheck() {
		System.out.println(ScanUtil.validateIP("192.13"));
	}

}
