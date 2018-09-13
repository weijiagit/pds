/**
 * 
 */
package com.fykj.kernel._c.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author J
 */
@SuppressWarnings("serial")
public class JImpl<T> implements JPage<T> {
	
	private long totalRecordNumber=Long.MAX_VALUE;

	private int totalPageNumber;
	
	private JPageable pageable;
	
	private List<T> content=new ArrayList<T>();
	
	public static int caculateTotalPageNumber(long totalRecordNumber,int pageSize){
		if(totalRecordNumber < pageSize) {
			return 0;
		} else {
			if ((totalRecordNumber % pageSize) > 0) {
				return (int) (totalRecordNumber/pageSize);
			} else {
				return (int) (totalRecordNumber/pageSize-1);
			}
		}
	}
	
	public void caculatePageNumber() {
		setTotalPageNumber(caculateTotalPageNumber(totalRecordNumber, pageable.getPageSize()));
		if (pageable.getPageNumber() > totalPageNumber) {
			SimplePageRequest pageRequest=(SimplePageRequest)pageable;
			pageRequest.setPageNumber(totalPageNumber);
		}
		
	}

	public long getTotalRecordNumber() {
		return totalRecordNumber;
	}



	public void setTotalRecordNumber(long totalRecordNumber) {
		this.totalRecordNumber = totalRecordNumber;
	}



	public int getTotalPageNumber() {
		return totalPageNumber;
	}


	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}


	public JPageable getPageable() {
		return pageable;
	}


	public void setPageable(JPageable pageable) {
		this.pageable = pageable;
	}

	@JsonIgnore
	public List<T> getContent() {
		return content;
	}


	public void setContent(List<?> content) {
		this.content = (List<T>) content;
	}
	
	public int getDraw(){
		return 0;
	}
	
	public long getRecordsFiltered(){
		return totalRecordNumber;
	}
	
	public long getRecordsTotal(){
		return totalRecordNumber;
	}
	
	
	public List<T> getData(){
		return content;
	}
}
