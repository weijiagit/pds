package com.fykj.kernel._c.bean;

import com.fykj.kernel._c._i.JDataBinder;

public interface ObjectBinder<I,T> extends JDataBinder {

	T createObject(I content);
	
}
