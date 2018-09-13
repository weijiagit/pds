/**
 * 
 */
package com.fykj._b._core.message.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * 或许短信验证码
 * ClassName: MessageInVO
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月18日 下午2:04:39
 *
 */

public class MessageInVO  implements JInputModel{
	
	private String phoneNum; //手机号
	private String formSource; // app 

	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneName) {
		this.phoneNum = phoneName;
	}
	public String getFormSource() {
		return formSource;
	}
	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}
	

}
