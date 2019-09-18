package com.testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.pref.Persist;

public class Testing {
	public static void main(String... strings) {
		System.out.println(searchPort(443));
		try {
			System.out.println(Persist.loadPreferences());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
