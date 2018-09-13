package com.fykj.kernel._c.async;

import java.util.concurrent.Callable;

import com.fykj.util.JUniqueUtils;

public abstract class CallableTask<V> implements Callable<V> {
	
	 protected String name(){
		 return JUniqueUtils.unique();
	 }
	
}
