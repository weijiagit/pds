package com.fykj.kernel.springjpa.query2;


import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;

public class JSPIFoundService
{
	
	public JJPASPI getSpi(){
		PersistenceProviderResolver persistenceProviderResolver=PersistenceProviderResolverHolder.getPersistenceProviderResolver();
		List<PersistenceProvider> persistenceProviders=persistenceProviderResolver.getPersistenceProviders();
		if(persistenceProviders!=null&&!persistenceProviders.isEmpty()){
			for(PersistenceProvider persistenceProvider:persistenceProviders){
				if(persistenceProvider instanceof HibernatePersistenceProvider){
					return JJPASPI.HIBERNATE;
				}
			}
		}
		return JJPASPI.EMPTY;
	} 
	
}
