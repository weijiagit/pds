package com.fykj.kernel.springjpa._hibernate;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2008Dialect;

public class CustomSQLServer2008Dialect  extends SQLServer2008Dialect{

	
	public CustomSQLServer2008Dialect() {
		super();
		registerHibernateType(Types.NVARCHAR, "string");
		registerHibernateType(Types.DECIMAL, "big_decimal");
	}
	
}
