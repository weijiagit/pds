package com.fykj.kernel;

/**
 * 用户登陆信息失败抛出异常
 *Title:SessionUserException
 * 
 * <p>Description:</p>
 * <p>Company:</p>
 *@author zhangj
 *@date 2017年12月20日
 */
public class SessionUserException extends BusinessException {

	public SessionUserException(Exception e) {
		super(e);
	}

	public SessionUserException(String e) {
		super(e);
	}

}

