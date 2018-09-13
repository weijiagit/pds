package com.fykj.kernel._c.model;


public class SimplePageRequest implements JPageable {

	private static final int DEFAULT_SIZE_PER_PAGE = 100;
	
	private int pageNumber;
	
	private int pageSize=DEFAULT_SIZE_PER_PAGE;
	
	private JOrder order;
	
	public SimplePageRequest(int pageNumber,int pageSize) {
		this.pageNumber=pageNumber;
		this.pageSize=pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public JOrder getOrder() {
		return order;
	}

	public void setOrder(JOrder order) {
		this.order = order;
	}
	
	public void setOrder(String column,String ascDesc){
		JOrder order=new JOrder();
		order.setColumn(column);
		order.setType(ascDesc);
		this.order=order;
	}
	
}
