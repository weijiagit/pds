package com.fykj.kernel.springjpa.query2;


import java.util.Map;

import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.springjpa.meta.JEntityUtilService;

public class JSingleEntityQueryMeta implements JModel {

	public static enum SqlType{
		JPQL,NATIVE
	}
	
	public static final String ALIAS="als";
	
	private final Class<?> entityClass;
	
	private JCondition condition;
	
	private JOrder order;
	
	private JSingleEntityQuery singleEntityQuery;
	
	public JSingleEntityQueryMeta(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	public JSingleEntityQueryMeta(Class<?> entityClass,JSingleEntityQuery singleEntityQuery) {
		this.entityClass=entityClass;
		this.singleEntityQuery=singleEntityQuery;
	}
	
	void setSingleEntityQuery(JSingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	public JCondition condition(){
		condition= new JCondition(entityClass);
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public JCondition conditionDefault(){
		condition= new JCondition(entityClass).equals("isAvailable",Availability.available.ordinal());
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public JOrder order() {
		order=new JOrder(entityClass);
		order.setSingleEntityQuery(singleEntityQuery);
		return order;
	}
	
	public String toJPQL(){
		String clause="from "+entityClass.getSimpleName()+" "+ALIAS;
		if(condition!=null){
			clause=clause+" "+condition.toWhereClause();
		}
		if(order!=null){
			clause=clause+" "+order.toOrderClause();
		}
		return clause;
	}
	
	public String toNative(){
		String clause= toSelectCause(SqlType.NATIVE)
				+ " from "
				+JEntityUtilService.get().getEntityModelMeta(entityClass).getTableName()
				+" "+ALIAS;
		if(condition!=null){
			clause=clause+" "+condition.toWhereClause(SqlType.NATIVE);
		}
		if(order!=null){
			clause=clause+" "+order.toOrderClause(SqlType.NATIVE);
		}
		return clause;
	}
	
	public Map<String, Object> toParams(){
		return condition.toParams();
	}
	
	public String toSelectCause(){
		return JEntityUtilService.get().selectCause(entityClass, ALIAS);
	}
	
	public String toSelectCause(SqlType sqlType){
		return JEntityUtilService.get().selectCause(sqlType,entityClass, ALIAS);
	}
	
	public Class<?> entityClass() {
		return entityClass;
	}
	
}
