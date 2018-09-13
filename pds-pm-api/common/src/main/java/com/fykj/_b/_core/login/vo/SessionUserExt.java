/**
 * 
 */
package com.fykj._b._core.login.vo;

import java.io.Serializable;
import java.util.Map;

import com.fykj.kernel._c.model.SessionUser;

/**
 * @author zhengzw
 *
 */
public class SessionUserExt implements Serializable, Cloneable {

	private static final long serialVersionUID = -3881386316871931612L;

	private SessionUser sessionUser;

	private Map<String, Object> role;

	private Map<String, Object> menu;

	private Map<String, Object> resources;

	private Map<String, Object> element;

	public SessionUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Map<String, Object> getRole() {
		return role;
	}

	public void setRole(Map<String, Object> role) {
		this.role = role;
	}

	public Map<String, Object> getMenu() {
		return menu;
	}

	public void setMenu(Map<String, Object> menu) {
		this.menu = menu;
	}

	public Map<String, Object> getResources() {
		return resources;
	}

	public void setResources(Map<String, Object> resources) {
		this.resources = resources;
	}

	public Map<String, Object> getElement() {
		return element;
	}

	public void setElement(Map<String, Object> element) {
		this.element = element;
	}
}
