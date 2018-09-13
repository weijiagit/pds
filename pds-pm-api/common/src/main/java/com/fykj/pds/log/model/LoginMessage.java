package com.fykj.pds.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fykj.kernel._c.model.AbstractEntity;


@Entity
@Table(name = "t_login_message")
public class LoginMessage extends AbstractEntity {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;
	
	//操作人名称
	@Column(name = "login_name")
	private String loginName;
	//操作人账号
	@Column(name = "loginAccount")
	private String loginAccount;
	//登陆时间
	@Column(name = "sign_in_time")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date signInTime;
	//退出时间
	@Column(name = "sign_out_time")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date signOutTime;
	//操作时间
	@Column(name = "operate_time")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date operateTime;
	//日志类型
	@Column(name = "log_type")
	private String logType;
	//操作内容
	@Column(name = "project_object")
	private String projectObject;
	//模块名称
	@Column(name = "project_module")
	private String projectModule;

	public String getProjectModule() {
		return projectModule;
	}

	public void setProjectModule(String projectModule) {
		this.projectModule = projectModule;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getProjectObject() {
		return projectObject;
	}

	public void setProjectObject(String projectObject) {
		this.projectObject = projectObject;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}


}
