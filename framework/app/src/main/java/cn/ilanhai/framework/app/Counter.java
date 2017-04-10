package cn.ilanhai.framework.app;

import java.util.concurrent.atomic.*;

public class Counter {

	public AtomicLong totalCounter = new AtomicLong(0);
	public AtomicLong currentCounter = new AtomicLong(0);
	public AtomicLong successCounter = new AtomicLong(0);
	public AtomicLong failureCounter = new AtomicLong(0);

	public Counter() {

	}

	@Override
	public String toString() {
		return String.format("counter:total=%s current=%s success=%s failure=%s",
				totalCounter.get(), currentCounter.get(), successCounter.get(),
				failureCounter.get());
	}
}
