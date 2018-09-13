package com.fykj.kernel.springjpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fykj.kernel._c.model.AbstractEntity;

@NoRepositoryBean
public interface JSpringJpaRepository<T extends AbstractEntity,ID extends Serializable>
	extends PagingAndSortingRepository<T, ID>,SingleEntityRepo<T, ID>{
	
//	@Override
//	@Query(value="select t from #{#entityName} t")
//	public List<T> getModelsByPage(JPagination pagination);
	
}
