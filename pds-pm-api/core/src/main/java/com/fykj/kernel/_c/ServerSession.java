package com.fykj.kernel._c;

import javax.servlet.http.HttpServletRequest;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel._c.model.SessionUser;

public class ServerSession implements JModel{

	private transient HttpServletRequest request;
	
	private SessionUser sessionUser;
	
	private String jwt;
	
	private String _token;

	public SessionUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String get_token() {
		return _token;
	}

	public void set_token(String _token) {
		this._token = _token;
	}

}
