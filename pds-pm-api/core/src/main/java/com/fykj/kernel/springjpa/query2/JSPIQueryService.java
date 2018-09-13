package com.fykj.kernel.springjpa.query2;

import java.util.List;

import javax.persistence.Query;

public interface JSPIQueryService {

	<T> T getSingleResult(Query query,JQuery<?> _query);
	
	<T> List<T> getResultList(Query query,JQuery<?> _query);
	
	Query createJPQLQuery(String jpql,JQuery<?> _query);
	
	Query createNamedQuery(String namedSql,JQuery<?> _query);
	
	Query createNativeQuery(String nativeSql,JQuery<?> _query);
}
