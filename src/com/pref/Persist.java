package com.pref;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Persist {

	private final static int PORT_TIMEOUT = 1000;
	private final static int IP_TIMEOUT = 1000;
	private final static int PORT_THREADS = 16;
	private final static int IP_THREADS = 8;

	private static final String FILENAME = "scanner_pref.json";
	private static Gson gson = new GsonBuilder().create();

	public static void savePreferences() {

	}

	public static Preference loadPreferences() throws IOException {
		File file = new File(FILENAME);
		if (file.exists()) {
			Preference pref = gson.fromJson(new FileReader(file), Preference.class);
			System.out.println("Creting");
			return pref;
		} else {
			Preference pref = new Preference();
			pref.setIP_THREADS(IP_THREADS);
			pref.setIP_TIMEOUT(IP_TIMEOUT);
			pref.setPORT_THREADS(PORT_THREADS);
			pref.setPORT_TIMEOUT(PORT_TIMEOUT);
			try (Writer writer = new FileWriter(file)) {
				Gson gson = new GsonBuilder().create();
				gson.toJson(pref, writer);
			} catch (IOException e) {
				throw new IOException(e);
			}
			return pref;
		}

	}

}
