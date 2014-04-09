package com.hg.ecommerce.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;


public interface BaseDao<T> {
	
	/**
	 * 直接添加一个实体Model
	 * @param param：实体
	 * @return：成功返回true
	 */
	Object add(T param);
	
	/**
	 * 同时添加多个实体Model
	 * @param params：Model的Collection集合
	 * @return：成功返回true
	 */
	List<Object> addMulti(Collection<T> params);
	
	boolean addByWrapper(SQLWrapper sqlWrapper);
	
	boolean update(T param);
	
	boolean updateByWrapper(SQLWrapper sqlWrapper);
	
	boolean upsert(T param);
	
	boolean delete(T param);
	
	boolean deleteById(Object...id);
	
	boolean deleteByWrapper(SQLWrapper sqlWrapper);
	
	T findOneById(Object...id);
		
	T findOneByWrapper(SQLWrapper sqlWrapper);
	
	List<T> findAll();
	
	//wrapper
	List<T> findAllByWrapper(SQLWrapper sqlWrapper);
	
	List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,Pageable pageable);
	
	List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,Sortable sortable);
	
	List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,Pageable pageable,Sortable sortable);
	
	//native
	List<Map<String, Object>> findByNativeQuery(String sql);
	
	//all rows
	long getCount();
	
	long getCountByWrapper(SQLWrapper sqlWrapper);
	
	
}