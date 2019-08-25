package com.threads;

public interface PortListener {
	void onOpen(final int port, final int progress);

	void onClose(final int port, final int progress);

	void isComplete(final boolean done);
}