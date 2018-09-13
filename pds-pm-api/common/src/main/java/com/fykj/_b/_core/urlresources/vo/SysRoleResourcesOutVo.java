/**
 * 
 */
package com.fykj._b._core.urlresources.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author zhengzw
 *
 */
public class SysRoleResourcesOutVo implements JOutputModel {
	
	private static final long serialVersionUID = 50689348056915537L;

	private String id;
	
	private String name;
	
	private String url;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
