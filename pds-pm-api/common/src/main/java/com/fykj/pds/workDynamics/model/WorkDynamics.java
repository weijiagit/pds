package com.fykj.pds.workDynamics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

@Entity
@Table(name = "t_work_dynamics")
public class WorkDynamics extends AbstractEntity {
	

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "content")
	private String content;
	

	@Column(name = "img_name")
	private String imgName;
	

	@Column(name = "img_url")
	private String imgUrl;
	

	@Column(name = "department_name")
	private String departmentName;


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getImgName() {
		return imgName;
	}


	public void setImgName(String imgName) {
		this.imgName = imgName;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	

	
}
