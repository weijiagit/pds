package com.fykj.kernel._c.async;

import com.fykj.util.JUniqueUtils;

public abstract class Task implements Runnable {
	
	 protected String name(){
		 return JUniqueUtils.unique();
	 }
	
}
