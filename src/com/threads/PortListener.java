package com.threads;

public interface PortListener {
	void onOpen(final int p0, final int p1);

	void onClose(final int p0, final int p1);

	void isComplete(final boolean done);
}