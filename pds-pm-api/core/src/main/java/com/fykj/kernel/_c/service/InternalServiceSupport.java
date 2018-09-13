package com.fykj.kernel._c.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.AbstractEntity;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPageable;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.springjpa.SingleEntityRepo;
import com.fykj.kernel.springjpa.query2.JSingleEntityQuery;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public class InternalServiceSupport<T extends AbstractEntity> extends SpringServiceFactorySupport implements IServiceSupport<T,String> {
	
	protected final Logger LOGGER= LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	protected Class<?> entityClass=null;
	
	public InternalServiceSupport() {
		ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass=(Class<T>) type.getActualTypeArguments()[0];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOnly( T object) {
		LOGGER.info("object",object);
		proxyOnSave(getRepo(), ServerSessionHolder.getSessionUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOnly( T object){
		LOGGER.info("object",object);
		proxyOnUpdate(getRepo(), ServerSessionHolder.getSessionUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete( String id,Class<?>... entryClass) {
		LOGGER.info("id",id);
		T abstractEntity= getRepo().getModel(id, entryClass);
		abstractEntity.setIsAvailable(Availability.unavailable);
		updateOnly(abstractEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getById( String id,Class<?>... entryClass) {
		LOGGER.info("id",id);
		return getRepo().getModel(id, entryClass);
	}


	/**
	 * fill in common info.  to execute 
	 * @param authorizer    generally its login user
	 */
	private T proxyOnSave(SingleEntityRepo<T , ?> repo, SessionUser authorizer, T baseModel){
		LOGGER.info("repo",repo);
		LOGGER.info("authorizer",authorizer);
		LOGGER.info("baseModel",baseModel);
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
		LOGGER.info("repo",repo);
		LOGGER.info("baseModel",baseModel);
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
		LOGGER.info("repo",repo);
		LOGGER.info("authorizer",authorizer);
		LOGGER.info("baseModel",baseModel);
		baseModel.setModifierId(authorizer.getId());
		baseModel.setModifyDate(new Date());
		repo.saveModel(baseModel);
		return baseModel;
	}
	
	private T proxyOnUpdate(SingleEntityRepo<T, ?> repo, T baseModel){
		LOGGER.info("repo",repo);
		LOGGER.info("baseModel",baseModel);
		baseModel.setModifierId("SYS-JOB");
		baseModel.setModifyDate(new Date());
		repo.saveModel(baseModel);
		return baseModel;
	}


	protected SimplePageRequest toPageRequest(JPageable pageable){
		LOGGER.info("pageable",pageable);
		return new SimplePageRequest(pageable.getPageNumber(), pageable.getPageSize());
	}
	
	public void deleteAllByIds( List<String> ids,Class<?>... entryClass) {
		LOGGER.info("ids",ids);
		for(String id : ids){
			delete(id,entryClass);
		}
	}
	
	@Override
	public void deleteAllByModels( List<T> models,Class<?>... entryClass) {
		LOGGER.info("models",models);
		for(T model:models){
			delete(model.getId());
		}
	}
	
	@Override
	public void delete( T model) {
		LOGGER.info("model",model);
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
		return null;
	}
	
	@Override
	protected boolean isCanRegister() {
		return false;
	}
	
	/**
	 * use {@link #singleEntityQuery2()} instead of this.
	 * @return
	 */
	@Deprecated
	public JSingleEntityQuery singleEntityQuery(){
		return new JSingleEntityQuery(entityClass, em);
	}
	
	public com.fykj.kernel.springjpa.query2.JSingleEntityQuery singleEntityQuery2(){
		return new com.fykj.kernel.springjpa.query2.JSingleEntityQuery(entityClass, em);
	}

	@Override
	public void saveOnlyForSchedlued(T object) {
		LOGGER.info("object",object);
		proxyOnSave(getRepo(), object);
	}

	@Override
	public void updateOnlyForSchedlued(T object) {
		proxyOnUpdate(getRepo(), object);
	}
}
