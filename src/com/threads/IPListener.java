package com.threads;

public interface IPListener {
	void onAlive(final String p0, final int p1);

	void onSleep(final String p0, final int p1);

	void isComplete(final boolean done);
}
