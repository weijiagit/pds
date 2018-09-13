package com.fykj.kernel._c.service;

import com.fykj.kernel._c.model.AbstractEntity;

public class SingleEntityManagerGetter {

	private static final SingleEntityManagerGetter INSTANCE=new SingleEntityManagerGetter();
	
	public static final SingleEntityManagerGetter get(){
		return INSTANCE;
	}
	
	public final <T extends AbstractEntity> SingleEntityManager<T> getInstance(Class<T> clazz){
		SingleEntityManager<T> singleEntityManager=new SingleEntityManager<T>(SingleEntityQueryService.getEm(), clazz);
		return singleEntityManager;
	}
	
}
