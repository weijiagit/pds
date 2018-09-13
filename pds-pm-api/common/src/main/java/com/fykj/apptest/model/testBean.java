/**
 * 
 */
package com.fykj.apptest.model;

import com.fykj.kernel._c.model.JModel;

/**
 * ClassName: testBean
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月11日 上午10:24:56
 *
 */

public class testBean implements JModel {
	
	private String id;
	private String name;
	private String age;
	private String type;
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public testBean(String id, String name, String age, String type) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.type = type;
	}
	public testBean() {
		super();
	}
	
	

}
