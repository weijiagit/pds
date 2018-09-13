package com.fykj.kernel._c.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.AbstractEntity;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPageable;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.springjpa.SingleEntityRepo;
import com.fykj.kernel.springjpa.meta.JEntityUtilService;
import com.fykj.kernel.springjpa.query2.JSingleEntityQueryMeta.SqlType;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public class SingleEntityManager<T extends AbstractEntity> implements IServiceSupport<T,String> {
	
	private EntityManager em;
	
	EntityManager getEm() {
		if(em==null){
			em=SingleEntityQueryService.getEm();
		}
		return em;
	}
	
	
	
	protected Class<T> entityClass;
	
	public SingleEntityManager(EntityManager em,Class<T> entityClass) {
		this.entityClass=entityClass;
		this.em=em;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOnly( T object) {
		proxyOnSave(getRepo(), ServerSessionHolder.getSessionUser(), object);
	}
	
	@Override
	public void saveOnlyForSchedlued( T object) {
		proxyOnSave(getRepo(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOnly( T object){
		proxyOnUpdate(getRepo(), ServerSessionHolder.getSessionUser(), object);
	}
	
	@Override
	public void updateOnlyForSchedlued( T object){
		proxyOnUpdate(getRepo(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete( String id,Class<?>... entryClass) {
		T abstractEntity= getRepo().getModel(id, entryClass);
		abstractEntity.setIsAvailable(Availability.unavailable);
		updateOnly(abstractEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getById( String id,Class<?>... entryClass) {
		return getRepo().getModel(id, entryClass);
	}


	/**
	 * fill in common info.  to execute 
	 * @param authorizer    generally its login user
	 */
	private T proxyOnSave(SingleEntityRepo<T , ?> repo, SessionUser authorizer, T baseModel){
		baseModel.setVersion(0);
		baseModel.setCreatorId(authorizer.getId());
		baseModel.setCreateDate(new Date());
		baseModel.setModifierId(authorizer.getId());
		baseModel.setModifyDate(new Date());
		baseModel.setIsAvailable(Availability.available);
		repo.saveModel(baseModel);
		return baseModel;
	}
	
	private T proxyOnSave(SingleEntityRepo<T , ?> repo, T baseModel){
		baseModel.setVersion(0);
		baseModel.setCreatorId("SYS-JOB");
		baseModel.setCreateDate(new Date());
		baseModel.setModifierId("SYS-JOB");
		baseModel.setModifyDate(new Date());
		baseModel.setIsAvailable(Availability.available);
		repo.saveModel(baseModel);
		return baseModel;
	}
	
	/**
	 * fill in common info.
	 * also validate whether the version changes, then to execute 
	 */
	private T proxyOnUpdate(SingleEntityRepo<T, ?> repo, SessionUser authorizer, T baseModel){
		baseModel.setModifierId(authorizer.getId());
		baseModel.setModifyDate(new Date());
		repo.updateModel(baseModel);
		return baseModel;
	}
	
	private T proxyOnUpdate(SingleEntityRepo<T, ?> repo, T baseModel){
		baseModel.setModifierId("SYS-JOB");
		baseModel.setModifyDate(new Date());
		repo.updateModel(baseModel);
		return baseModel;
	}


	protected SimplePageRequest toPageRequest(JPageable pageable){
		return new SimplePageRequest(pageable.getPageNumber(), pageable.getPageSize());
	}
	
	public void deleteAllByIds( List<String> ids,Class<?>... entryClass) {
		for(String id : ids){
			delete(id,entryClass);
		}
	}
	
	@Override
	public void deleteAllByModels( List<T> models,Class<?>... entryClass) {
		for(T model:models){
			delete(model.getId());
		}
	}
	
	@Override
	public void delete( T model) {
		delete(model.getId());
	}
	
	@Override
	public List<T> getAllModes(Class<?>... entryClass){
		return (List<T>) getRepo().getAllModels(entryClass);
	}
	
	/**
	 * 物理删除
	 * @param serviceContext
	 * @param model
	 */
	@Override
	public void deletePermanently(T model){
		getRepo().deleteModel(model);
	}
	
	/**
	 * 批量物理删
	 * @param serviceContext
	 * @param models
	 */
	@Override
	public void deletePermanentlyByModels(List<T> models,Class<?>... entryClass){
		for(T model : models){
			deletePermanently(model);
		}
	}
	
	@Override
	public void saveAllOnly( Iterable<T> objects,Class<?>... entryClass) {
		getRepo().saveAllModels(objects, entryClass);
	}
	
	/**
	 * override the method to provide the real repository.
	 */
	@Override
	public SingleEntityRepo<T, String> getRepo() {
		return new InternalRepo();
	}
	
	public class InternalRepo implements SingleEntityRepo<T, String>{
		@Override
		public void saveModel(T baseModel) {
			getEm().persist(baseModel);
		}

		@Override
		public int updateModel(T baseModel) {
			getEm().merge(baseModel);
			return 1;
		}

		@Override
		public T getModel(String id, Class<?>... entryClass) {
			return getEm().getReference(entityClass, id);
		}

		@Override
		public void deleteModel(T baseModel) {
			getEm().remove(getEm().contains(baseModel) ? baseModel : getEm().merge(baseModel));
		}

		@Override
		public void markModelDeleted(T baseModel) {
			throw new IllegalStateException("the method is not supported.");
		}

		@Override
		public void markModelDeleted(String id, Class<?>... entryClass) {
			throw new IllegalStateException("the method is not supported.");
		}

		@Override
		public List<T> getAllModels(Class<?>... entryClass) {
			throw new IllegalStateException("the method is not supported.");
		}

		@Override
		public void saveAllModels(Iterable<T> objects, Class<?>... entryClass) {
			for (T entity : objects) {
				saveModel(entity);
			}
		}
		
	}
	
	public com.fykj.kernel.springjpa.query2.JSingleEntityQuery singleEntityQuery2(){
		return new com.fykj.kernel.springjpa.query2.JSingleEntityQuery(entityClass, getEm());
	}
	
	public String selectCause(String... alias){
		return JEntityUtilService.get().selectCause(entityClass, alias);
	}
	
	public String selectCause(SqlType sqlType,String... alias){
		return JEntityUtilService.get().selectCause(sqlType,entityClass, alias);
	}
}
