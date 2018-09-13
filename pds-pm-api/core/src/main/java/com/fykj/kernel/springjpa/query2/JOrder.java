package com.fykj.kernel.springjpa.query2;


import java.util.ArrayList;
import java.util.List;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.springjpa.meta.JEntityUtilService;
import com.fykj.kernel.springjpa.query2.JSingleEntityQueryMeta.SqlType;
import com.fykj.util.JStringUtils;

public class JOrder implements JModel {

	private Class<?> entityClass;
	
	private JSingleEntityQuery singleEntityQuery;
	
	public JOrder(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	void setSingleEntityQuery(JSingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	private List<OrderSlice> orderClause=new ArrayList<OrderSlice>();
	
	private boolean validate(String property) throws IllegalArgumentException{
		return true;
	}
	
	public JSingleEntityQuery ready(){
		return singleEntityQuery;
	}
	
	public String toOrderClause(SqlType sqlType){
		StringBuffer stringBuffer=new StringBuffer("");
		String prefix=",";
		for(OrderSlice orderSlice:orderClause){
			String clause=JSingleEntityQueryMeta.ALIAS+"."
						+(SqlType.JPQL==sqlType? orderSlice.propertyName:orderSlice.columnName)
						+" " + orderSlice.orderType;
			stringBuffer.append(prefix);
			stringBuffer.append(" "+clause+" ");
		}
		String inner=stringBuffer.toString().replaceFirst(prefix, "").trim();
		return JStringUtils.isNullOrEmpty(inner)?"":(" order by "+inner);
	}
	
	public String toOrderClause(){
		return toOrderClause(SqlType.JPQL);
	}
	
	public static enum OrderType{
		ASC("ASC"),DESC("DESC");
		private OrderType(String type){
			
		}
	}
	
	private JOrder append(String property,String orderType){
		OrderSlice orderSlice=new OrderSlice();
		orderSlice.propertyName=property;
		orderSlice.columnName=JEntityUtilService.get()
				.getEntityColumnMeta(entityClass, property)
				.getColumn();
		orderSlice.orderType=orderType;
		orderClause.add(orderSlice);
//		orderClause.add(JSingleEntityQueryMeta.ALIAS+"."+property+" "+orderType);
		return this;
	}
	
	public JOrder asc(String property){
		validate(property);
		append(property, OrderType.ASC.name());
		return this;
	}
	
	public JOrder desc(String property){
		validate(property);
		append(property, OrderType.DESC.name());
		return this;
	}
	
	
	/**
	 * JSingleEntityQueryMeta.ALIAS+"."+property+" "+orderType
	 * @author JIAZJ
	 *
	 */
	private class OrderSlice{
		
		private String propertyName;
		
		private String columnName;

		private String orderType;
		
	}
	
}
