package com.fykj.pds.leaderMessage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;


@Entity
@Table(name = "t_leader_message")
public class LeaderMessage extends AbstractEntity {

	
	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "content")
	private String content;
	
	@Column(name = "user_name")
	private String userName;

	@Column(name = "dept_id")
	private String deptId;

	@Column(name = "leader_id")
	private String leaderId;

	@Column(name = "finish_state")
	private String finishState;


	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getFinishState() {
		return finishState;
	}

	public void setFinishState(String finishState) {
		this.finishState = finishState;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
