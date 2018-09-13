package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class DepartProjectAdminOutVO implements JOutputModel {

	/**  
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
