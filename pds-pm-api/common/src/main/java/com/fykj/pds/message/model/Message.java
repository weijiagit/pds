package com.fykj.pds.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;



@Entity
@Table(name = "t_sys_message")
public class Message  extends AbstractEntity {

	
	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "content")
	private String content;
	
	@Column(name = "type")
	private String type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
