package com.hg.ecommerce.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hg.ecommerce.dao.BaseDao;
import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;
import com.hg.ecommerce.model.support.AnnotatedModel;
import com.hg.ecommerce.model.support.EntityObject;


public class BaseDaoImpl<T> implements BaseDao<T>{
	
	private Class<T> cls;
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		@SuppressWarnings("rawtypes")
		Class clazz = getClass();
		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					this.cls = (Class<T>) args[0];
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}
	}
	
	@Override
	public boolean add(T param) {
		String query = SQLWrapper.instance().insert((EntityObject) param).setModel(cls.getSimpleName()).getQuery();
		System.out.println("add T:"+query);
		if(0<getJdbcTemplate().update(query)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addMulti(Collection<T> params) {
		int count = 0;
		for(T param : params){
			getJdbcTemplate().update(SQLWrapper.instance().insert((EntityObject) param).setModel(cls.getSimpleName()).getQuery());
			count++;
		}
		if(count==params.size()){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addByWrapper(SQLWrapper sqlWrapper) {
		if(0<getJdbcTemplate().update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(T param) {
		if(0<getJdbcTemplate().update(SQLWrapper.instance().update((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateByWrapper(SQLWrapper sqlWrapper) {
		if(0<getJdbcTemplate().update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean upsert(T param) {
		if(0<getJdbcTemplate().update(SQLWrapper.instance().upsert((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(T param) {
		if(0<getJdbcTemplate().update(SQLWrapper.instance().delete((EntityObject) param).setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteById(String...id) {
		AnnotatedModel meta = new AnnotatedModel((Class<? extends EntityObject>) cls);
		String[] keys = (String[]) meta.getPrimaryKeys().toArray();
		SQLWrapper wrapper = SQLWrapper.instance();
		wrapper.delete().setModel(cls.getSimpleName()).where();
		for(int i=0,size=id.length; i<size; i++){
			wrapper.eq(keys[i], id[i]);
		}
		if(0<getJdbcTemplate().update(wrapper.getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByWrapper(SQLWrapper sqlWrapper) {
		if(0<getJdbcTemplate().update(sqlWrapper.setModel(cls.getSimpleName()).getQuery())){
			return true;
		}
		return false;
	}

	@Override
	public T findOneById(String...id) {
		@SuppressWarnings("unchecked")
		AnnotatedModel meta = new AnnotatedModel((Class<? extends EntityObject>) cls);
		String[] keys = (String[]) meta.getPrimaryKeys().toArray();
		SQLWrapper wrapper = SQLWrapper.instance();
		wrapper.selectAll().setModel(cls.getSimpleName()).where();
		for(int i=0,size=id.length; i<size; i++){
			wrapper.eq(keys[i], id[i]);
		}
		return getJdbcTemplate().queryForObject(wrapper.getQuery(), cls);
	}

	@Override
	public T findOneByWrapper(SQLWrapper sqlWrapper) {
		return getJdbcTemplate().queryForObject(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAll() {
		return getJdbcTemplate().queryForList(SQLWrapper.instance().selectAll().setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapper(SQLWrapper sqlWrapper) {
		return getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,Pageable pageable) {
		List<T> totaList = getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls);
		pageable.setPageCount(totaList.size());
		return getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).limit(pageable).getQuery(),cls);
	}

	@Override
	public List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,Sortable sortable) {
		return getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).orderBy(sortable).getQuery(), cls);
	}

	@Override
	public List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,Pageable pageable, Sortable sortable) {
		return getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).orderBy(sortable).limit(pageable).getQuery(), cls);
	}

	@Override
	public List<T> findByNativeQuery(String sql) {
		return getJdbcTemplate().queryForList(sql, cls);
	}

	@Override
	public long getCount() {
		return getJdbcTemplate().queryForList(SQLWrapper.instance().selectAll().setModel(cls.getSimpleName()).getQuery(), cls).size();
	}

	@Override
	public long getCountByWrapper(SQLWrapper sqlWrapper) {
		return getJdbcTemplate().queryForList(sqlWrapper.setModel(cls.getSimpleName()).getQuery(), cls).size();
	}

	
	


}
