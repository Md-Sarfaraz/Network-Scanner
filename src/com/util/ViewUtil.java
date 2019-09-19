package com.util;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class ViewUtil {
	public static void validInputIP(KeyEvent e) {
		char c = e.getKeyChar();
		if (!((c >= '0') && (c <= '9') || (c == '.') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			Toolkit.getDefaultToolkit().beep();
			e.consume();
		}
	}

	public static void validInputPort(KeyEvent e) {
		char c = e.getKeyChar();
		if (!((c >= '0') && (c <= '9') || (c == ',') || (c == '-') || (c == KeyEvent.VK_BACK_SPACE)
				|| (c == KeyEvent.VK_DELETE))) {
			Toolkit.getDefaultToolkit().beep();
			e.consume();
		}
	}

	public static void checkTargetScan() {

	}

}
