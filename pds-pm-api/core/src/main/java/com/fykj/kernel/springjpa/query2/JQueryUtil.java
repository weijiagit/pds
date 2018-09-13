package com.fykj.kernel.springjpa.query2;

import javax.persistence.EntityManager;

public class JQueryUtil {

	
	public static EntityManager getEntityManager(JQuery<?> query){
		return query.em;
	}
	
	
}
