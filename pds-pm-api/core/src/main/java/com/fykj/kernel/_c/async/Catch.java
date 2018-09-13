package com.fykj.kernel._c.async;

public abstract class Catch {
	
	private boolean done;
	
	public abstract void cat(Throwable error);

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
}
