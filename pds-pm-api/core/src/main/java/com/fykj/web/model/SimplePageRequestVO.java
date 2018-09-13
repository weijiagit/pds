package com.fykj.web.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


public class SimplePageRequestVO implements Serializable ,Cloneable {

	@ApiModelProperty(value="分页当前页数")
	private int page;
	
	@ApiModelProperty(value="分页每页显示条数")
	private int size;
	
	@ApiModelProperty(value="排序Column, 应该和排序方式一致，当前不需要传" ,example ="A,B,C..." )
	private String orderColumn;
	
	@ApiModelProperty(value="排序方式, 应该和排序Column一致，当前不需要传" ,example="ASC,DESC,ASC...")
	private String orderType;
	
	
	SimplePageRequestVO(){}

	public SimplePageRequestVO(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
