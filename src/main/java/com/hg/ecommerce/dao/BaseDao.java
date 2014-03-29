package com.hg.ecommerce.dao;

import java.util.Collection;
import java.util.List;

import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.Projections;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;


public interface BaseDao<T> {
	
	boolean add(T param);
	
	boolean addMulti(Collection<T> params);
	
	boolean addByWrapper(SQLWrapper sqlWrapper);
	
	boolean update(T param);
	
	boolean updateByWrapper(SQLWrapper sqlWrapper);
	
	boolean upsert(T param);
	
	boolean delete(T param);
	
	boolean deleteById(String id);
	
	boolean deleteByWrapper(SQLWrapper sqlWrapper);
	
	boolean deleteByIdByWrapper(String id,SQLWrapper sqlWrapper);
	
	T findOneById(String id);
	
	T findOneByIdByProjected(String id,Projections projections);
	
	T findOneByIdByWrapper(String id,SQLWrapper sqlWrapper);
		
	T findOneByProjectedByWrapper(Projections projections,SQLWrapper sqlWrapper);
	
	List<T> findAll();
	
	List<T> findAllByProjected();
	
	List<T> findAllByProjectedInPage();
	
	List<T> findAllByProjectedInOrder();
	
	List<T> findAllByProjectedInPageInOrder();
	
	//wrapper
	List<T> findAllByWrapper();
	
	List<T> findAllByWrapperInPage();
	
	List<T> findAllByWrapperInOrder();
	
	List<T> findAllByWrapperInPageInOrder();
	
	
	//projected and wrapper
	List<T> findAllByProjectedByWrapper();
	
	List<T> findAllByProjectedByWrapperInPage();
	
	List<T> findAllByProjectedByWrapperInOrder();
	
	List<T> findAllByProjectedByWrapperInPageInOrder(Projections projections,SQLWrapper sqlWrapper,Pageable pageable,Sortable sortable);
	
	//native
	List<T> findByNativeQuery();
	
	
}
