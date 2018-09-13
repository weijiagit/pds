package com.fykj.kernel._c;

public interface ServerSessionCollecter<I,O> {

	void collect(I source,O out);
	
}
