/**
 * 
 */
package com.fykj._b._core.menu.vo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class SysMenuSaveInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3106940770643432995L;
	
	@NotEmpty(message = "菜单名称不允许为空")
	@NotNull(message = "菜单名称不允许为空")
	@Length(max = 255, message = "菜单名称最大长度支持255个字符")
	private String name;
	
	@Length(max = 255, message = "菜单地址最大长度支持255个字符")
	private String url;
	
	@Length(max = 255, message = "菜单样式最大长度支持255个字符")
	private String cls;
	
	@Length(max =  255, message = "布局地址最大长度支持255个字符")
	private String layout;
	
	@NotNull(message = "菜单父节点不允许为空")
	@NotEmpty(message = "菜单父节点不允许为空")
	private String pid;
	
	@NotNull(message = "菜单排序不允许为空")
	@Digits(integer = 2, fraction = 0)
	@Min(value = 1, message = "菜单排序最小值为1")
	private int sequence;

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

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
