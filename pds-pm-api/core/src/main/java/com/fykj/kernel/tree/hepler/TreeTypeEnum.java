package com.fykj.kernel.tree.hepler;

public enum TreeTypeEnum {
	/**
	 * 平台首页导航页面type
	 */
	Html_Menu("html_menu");
	private final String value;

	private TreeTypeEnum(String value) {
		this.value = value;
	}
	public String getValue() {
        return value;
    }

}
