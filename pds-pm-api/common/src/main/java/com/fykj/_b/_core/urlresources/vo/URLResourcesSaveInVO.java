/**
 * 
 */
package com.fykj._b._core.urlresources.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class URLResourcesSaveInVO implements JInputModel {
	
	private static final long serialVersionUID = -6381943091793160886L;

	@NotNull(message = "URL名称不允许为空")
	@NotEmpty(message = "URL名称不允许为空")
	@Length(max = 255, message = "URL名称最大长度支持255个字符")
	private String name;
	
	@NotNull(message = "URL路径不允许为空")
	@NotEmpty(message = "URL路径不允许为空")
	@Length(max = 255, message = "URL路径最大长度支持255个字符")
	private String url;
	
	@Length(max = 255, message = "URL路径最大长度支持255个字符")
	private String description;

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
