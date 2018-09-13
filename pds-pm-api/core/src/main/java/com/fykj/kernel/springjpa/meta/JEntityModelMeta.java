package com.fykj.kernel.springjpa.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fykj.kernel._c.model.JModel;

public class JEntityModelMeta implements JModel{
	
	private List<JEntityColumnMeta> columnMetas=new ArrayList<JEntityColumnMeta>();
	
	private Class<?> entityClass;
	
	private String tableName;
	
	private String schema;
	
	public JEntityModelMeta(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	public Collection<JEntityColumnMeta> columnMetas() {
		return Collections.unmodifiableCollection(columnMetas);
	}

	void setColumnMetas(List<JEntityColumnMeta> columnMetas) {
		this.columnMetas = columnMetas;
	}
	
	void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}

	public String getSchema() {
		return schema;
	}

	void setSchema(String schema) {
		this.schema = schema;
	}
	
	
	
}
