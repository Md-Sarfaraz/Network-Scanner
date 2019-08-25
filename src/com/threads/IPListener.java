package com.threads;

public interface IPListener {
	void onAlive(final String host, final int progress);

	void onSleep(final String host, final int progress);

	void isComplete(final boolean done);
}
