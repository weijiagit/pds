package com.fykj.kernel._c.service;

import java.io.Serializable;
import java.util.List;

import com.fykj.kernel._c.model.AbstractEntity;
import com.fykj.kernel.springjpa.SingleEntityRepo;

/**
 * Created by J on 2016/3/9.
 */
public interface IServiceSupport<T extends AbstractEntity,ID extends Serializable> {
	void saveOnly(T object);
	
	void saveOnlyForSchedlued( T object);

	void updateOnly(T object);
	
	void updateOnlyForSchedlued(T object);

	void delete(String id,Class<?>... entryClass);
	
	void delete(T model);

	T getById(String id,Class<?>... entryClass);

	public void deleteAllByIds(List<String> ids,Class<?>... entryClass);
	
	public void deleteAllByModels(List<T> models,Class<?>... entryClass);
	
	public void deletePermanentlyByModels(List<T> models,Class<?>... entryClass);
	
	public List<T> getAllModes(Class<?>... entryClass);
	
	public void deletePermanently(T model);
	
	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public JPage<T> getsByPage(JPageable pagination) {
//		JPageImpl<T> page=new JPageImpl<T>();
//		List<T> records=getRepo().getModelsByPage(pagination);
//		page.setPageable(pagination);
//		page.setTotalRecordNumber(records.size());
//		page.setContent(records);
//		page.setTotalPageNumber(JPageImpl.caculateTotalPageNumber(records.size(), pagination.getPageSize()));
//		return page;
//	}

	SingleEntityRepo<T,ID> getRepo();
	
	void saveAllOnly(Iterable<T> objects,Class<?>... entryClass);

}
