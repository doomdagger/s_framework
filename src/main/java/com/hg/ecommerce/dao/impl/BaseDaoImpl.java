package com.hg.ecommerce.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hg.ecommerce.dao.BaseDao;
import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;
import com.hg.ecommerce.model.support.EntityObject;


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
		if(0<jdbcTemplate.update(SQLWrapper.instance().insert((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addMulti(Collection<T> params) {
		int count = 0;
		for(T param : params){
			jdbcTemplate.update(SQLWrapper.instance().insert((EntityObject) param).setModel(cls.getSimpleName()).getQuery());
			count++;
		}
		if(count==params.size()){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addByWrapper(SQLWrapper sqlWrapper) {
		if(0<jdbcTemplate.update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(T param) {
		if(0<jdbcTemplate.update(SQLWrapper.instance().update((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateByWrapper(SQLWrapper sqlWrapper) {
		if(0<jdbcTemplate.update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean upsert(T param) {
		if(0<jdbcTemplate.update(SQLWrapper.instance().upsert((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(T param) {
		if(0<jdbcTemplate.update(SQLWrapper.instance().delete((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteById(String id) {
		
		//if(0<jdbcTemplate.update(SQLWrapper.instance().delete().where().eq(, value)))
		//TODO:
		return false;
	}

	@Override
	public boolean deleteByWrapper(SQLWrapper sqlWrapper) {
		if(0<jdbcTemplate.update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public T findOneById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOneByWrapper(SQLWrapper sqlWrapper) {
		return jdbcTemplate.queryForObject(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAll() {
		return jdbcTemplate.queryForList(SQLWrapper.instance().selectAll().setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapper(SQLWrapper sqlWrapper) {
		return jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,Pageable pageable) {
		List<T> totaList = jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
		pageable.setPageCount(totaList.size());
		return jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).limit(pageable).getQuery(),cls);
	}

	@Override
	public List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,Sortable sortable) {
		return jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).orderBy(sortable).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,Pageable pageable, Sortable sortable) {
		return jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).orderBy(sortable).limit(pageable).getQuery(), cls);
	}

	@Override
	public List<T> findByNativeQuery(String sql) {
		return jdbcTemplate.queryForList(sql, cls);
	}

	@Override
	public long getCount() {
		return jdbcTemplate.queryForList(SQLWrapper.instance().selectAll().setModel(cls.getSimpleName()).getQuery(), cls).size();
	}

	@Override
	public long getCountByWrapper(SQLWrapper sqlWrapper) {
		return jdbcTemplate.queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls).size();
	}

	
	


}
