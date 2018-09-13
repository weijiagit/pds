package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JInputModel;

public class ProjectBatchUpdateInVO extends ProjectInfo implements JInputModel {

	/**
	 * 表单
	 */
	private String projectBatchUpdateInVOs;

	/**
	 *选择列字符串
	 */
	private String columnNamesString;

	public String getProjectBatchUpdateInVOs() {
		return projectBatchUpdateInVOs;
	}

	public void setProjectBatchUpdateInVOs(String projectBatchUpdateInVOs) {
		this.projectBatchUpdateInVOs = projectBatchUpdateInVOs;
	}

	public String getColumnNamesString() {
		return columnNamesString;
	}

	public void setColumnNamesString(String columnNamesString) {
		this.columnNamesString = columnNamesString;
	}
}
