package com.fykj._b._core.urlresources.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

public class URLResourcesEidtInVO implements JInputModel {
	
	private static final long serialVersionUID = -7997678015356805894L;

	@NotNull(message = "URL主键不允许为空")
	@NotEmpty(message = "URL主键不允许为空")
	@Length(max = 36, message = "URL主键最大长度支持36个字符")
	private String id;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
