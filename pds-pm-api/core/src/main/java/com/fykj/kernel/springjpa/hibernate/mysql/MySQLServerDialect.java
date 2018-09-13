package com.fykj.kernel.springjpa.hibernate.mysql;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

public class MySQLServerDialect extends MySQL5Dialect {
	
	public MySQLServerDialect() {
		super();
		registerHibernateType(Types.BIGINT, long.class.getName());
	}

}
