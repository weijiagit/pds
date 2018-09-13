package com.fykj.kernel.springjpa;

import java.io.Serializable;
import java.util.List;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * the basic entry to access the DB
 * @author JIAZJ
 *
 * @param <M>
 * @param <ID>
 */
public interface SingleEntityRepo<M extends AbstractEntity,ID extends Serializable> {
	
	public void saveModel(M baseModel); 
	
	public int updateModel(M baseModel);
	
	public M getModel(ID id,Class<?>... entryClass);
	
	/**
	 * delete physically
	 * @param baseModel
	 */
	public void deleteModel(M baseModel);

	public void markModelDeleted(M baseModel);
	
	public void markModelDeleted(ID id,Class<?>... entryClass);
	
	public List<M> getAllModels(Class<?>... entryClass);
	
	public void saveAllModels(Iterable<M> objects,Class<?>... entryClass);
	
	
}
