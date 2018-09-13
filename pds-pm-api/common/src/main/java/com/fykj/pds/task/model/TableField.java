package com.fykj.pds.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

@Entity
@Table(name = "t_table_field")
public class TableField extends AbstractEntity {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "field_name")
	private String fieldName;

	// 是否选中
	@Column(name = "is_chk")
	private String isChk;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getIsChk() {
		return isChk;
	}

	public void setIsChk(String isChk) {
		this.isChk = isChk;
	}
}
