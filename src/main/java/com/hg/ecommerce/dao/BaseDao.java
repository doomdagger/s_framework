package com.hg.ecommerce.dao;

import java.util.Collection;
import java.util.List;

import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;
import com.hg.ecommerce.model.support.EntityObject;


public interface BaseDao<T extends EntityObject> {
	
	/**
	 * 直接添加一个实体Model
	 * @param param：实体
	 * @return：成功返回true
	 */
	boolean add(T param);
	
	/**
	 * 同时添加多个实体Model
	 * @param params：Model的Collection集合
	 * @return：成功返回true
	 */
	boolean addMulti(Collection<T> params);
	
	boolean addByWrapper(SQLWrapper sqlWrapper);
	
	boolean update(T param);
	
	boolean updateByWrapper(SQLWrapper sqlWrapper);
	
	boolean upsert(T param);
	
	boolean delete(T param);
	
	boolean deleteById(String...id);
	
	boolean deleteByWrapper(SQLWrapper sqlWrapper);
	
	T findOneById(String...id);
		
	T findOneByWrapper(SQLWrapper sqlWrapper);
	
	List<T> findAll();
	
	//wrapper
	List<T> findAllByWrapper(SQLWrapper sqlWrapper);
	
	List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,Pageable pageable);
	
	List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,Sortable sortable);
	
	List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,Pageable pageable,Sortable sortable);
	
	//native
	List<T> findByNativeQuery(String sql);
	
	//all rows
	long getCount();
	
	long getCountByWrapper(SQLWrapper sqlWrapper);
	
	
}
