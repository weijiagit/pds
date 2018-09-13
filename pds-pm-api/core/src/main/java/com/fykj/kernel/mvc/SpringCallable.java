package com.fykj.kernel.mvc;

import java.util.concurrent.Callable;

import com.fykj.kernel._c.ServerSession;
import com.fykj.kernel._c.ServerSessionHolder;

public abstract class SpringCallable<T> implements Callable<T>  {

	private final ServerSession serverSession;
	
	public SpringCallable() {
		this.serverSession=ServerSessionHolder.getServerSession();
	}
	
	@Override
	public final T call() throws Exception {
		ServerSessionHolder.setServerSession(serverSession);
		return doCall();
	}
	
	protected abstract T doCall() throws Exception;
	
	
	
}
