/**
 * 
 */
package com.fykj._b._core.urlresources.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class URLResourcesPageInVO implements JInputModel {
	
	private static final long serialVersionUID = 8368357017001338908L;

	private String name;
	
	private String url;

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
}
