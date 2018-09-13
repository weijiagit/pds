/**
 * 
 */
package com.fykj._b._core.message.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * ClassName: Message
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月18日 下午3:39:55
 *
 */
@Entity
@Table(name = "t_message")
public class Message extends AbstractEntity {
	/**
	 * 手机号
	 */
	@Column(name ="phone_num")
	private String phoneNum;
	
	/**
	 * 验证码
	 */
	@Column(name ="code")
	private String code; //验证码
	
	/**
	 * 有效期 单位
	 */
	  @Column(name = "expire_date" , updatable = false)
	  @Temporal(value=TemporalType.TIMESTAMP)
	  private Date expireDate;
	
	/**
	 * 是否有效
	 * 校验成功既失效
	 */
	  @Column(name ="has_validity")
	  private boolean hasValidity = true;
	
	/**
	 * 数据来源
	 */
	  @Column(name ="form_source")
	  private String formSource;
	
	/**
	 * 是否发送
	 */
	  @Column(name ="has_send")
	  private boolean hasSend = false;
	
	/**
	 * 业务类型
	 */
	  @Column(name ="serve_type")
	  private String serveType;
	  
	  /**
	   * 发送结果
	   */
	  @Column(name ="send_result")
	  private String sendResult;



	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean isHasValidity() {
		return hasValidity;
	}

	public void setHasValidity(boolean hasValidity) {
		this.hasValidity = hasValidity;
	}

	public String getFormSource() {
		return formSource;
	}

	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}

	public boolean isHasSend() {
		return hasSend;
	}

	public void setHasSend(boolean hasSend) {
		this.hasSend = hasSend;
	}

	public String getServeType() {
		return serveType;
	}

	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	  


}
