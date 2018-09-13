package com.fykj.kernel._c.model;

import java.util.List;

public interface JPage<T>  extends JModel{

	JPageable getPageable();
	
	List<T> getContent();
	
	int getTotalPageNumber();
	
	long getTotalRecordNumber();
	
}
