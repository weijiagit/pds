package com.fykj.kernel._c.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fykj.kernel._c.model.JPageable;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.springjpa.query2.JJPQLQuery;
import com.fykj.kernel.springjpa.query2.JNamedQuery;
import com.fykj.kernel.springjpa.query2.JNativeQuery;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public abstract class ServiceSupport extends SpringServiceFactorySupport {
	
	protected final Logger LOGGER= LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	private EntityManager em;

	protected SimplePageRequest toPageRequest(JPageable pageable){
		LOGGER.info("pageable",pageable);
		return new SimplePageRequest(pageable.getPageNumber(), pageable.getPageSize());
	}
	
	protected JNativeQuery nativeQuery(){
		return new JNativeQuery(em);
	}
	
	protected JJPQLQuery jpqlQuery(){
		return new JJPQLQuery(em);
	}
	
	protected JNamedQuery namedQuery(){
		return new JNamedQuery(em);
	}
	
	
}
