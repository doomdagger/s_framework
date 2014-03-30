package com.hg.ecommerce.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hg.ecommerce.dao.BaseDao;
import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;


public class BaseDaoImpl<T> implements BaseDao<T>{
	
	private Class<T> cls;
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		this.cls =(Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public boolean add(T param) {
		//jdbcTemplate
		return false;
	}
	
	@Override
	public boolean addMulti(Collection<T> params) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean addByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(T param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upsert(T param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(T param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T findOneById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneByIdByProjected(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,
			Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,
			Pageable pageable, Sortable sortable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findByNativeQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCountByWrapper(SQLWrapper sqlWrapper) {
		// TODO Auto-generated method stub
		return 0;
	}


	
	


}
