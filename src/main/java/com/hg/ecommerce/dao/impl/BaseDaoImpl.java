package com.hg.ecommerce.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.hg.ecommerce.dao.BaseDao;
import com.hg.ecommerce.dao.support.Pageable;
import com.hg.ecommerce.dao.support.SQLWrapper;
import com.hg.ecommerce.dao.support.Sortable;
import com.hg.ecommerce.model.support.AnnotatedModel;
import com.hg.ecommerce.model.support.EntityObject;


public class BaseDaoImpl<T> implements BaseDao<T>{
	
	private Class<T> cls;
	
	AnnotatedModel meta;
	
	private String query;
	
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
		meta = new AnnotatedModel((Class<? extends EntityObject>) cls);
	}
	
	@Override
	public Object add(final T param) {
		this.query = SQLWrapper.instance().insert((EntityObject) param).setModel(meta.getTableName()).getQuery();
		final String preQuery = this.query;
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update((new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(preQuery,Statement.RETURN_GENERATED_KEYS);
				return preparedStatement;
			}
		}), keyHolder);
		return keyHolder.getKey()==null?null:keyHolder.getKey().intValue();
	}
	
	
	@Override
	public List<Object> addMulti(Collection<T> params) {
		List<Object> list = new ArrayList<Object>();
		for(T param : params){
			this.query = SQLWrapper.instance().insert((EntityObject) param).setModel(meta.getTableName()).getQuery();
			list.add(getJdbcTemplate().update(this.query));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).preparedInsert((Class<EntityObject>) cls);
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean update(T param) {
		this.query = SQLWrapper.instance().update((EntityObject) param).setModel(meta.getTableName()).getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean upsert(T param) {
		this.query = SQLWrapper.instance().upsert((EntityObject) param).setModel(meta.getTableName()).getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(T param) {
		this.query = SQLWrapper.instance().delete((EntityObject) param).setModel(meta.getTableName()).getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteById(Object...id) {
		AnnotatedModel meta = new AnnotatedModel((Class<? extends EntityObject>) cls);
		Object[] keys = (Object[]) meta.getPrimaryKeys().toArray();
		SQLWrapper wrapper = SQLWrapper.instance();
		wrapper.delete().setModel(meta.getTableName()).where();
		for(int i=0,size=id.length; i<size; i++){
			wrapper.eq(keys[i], id[i]);
		}
		this.query = wrapper.getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		if(0<getJdbcTemplate().update(this.query)){
			return true;
		}
		
		return false;
	}

	@Override
	public T findOneById(Object...id) {
		@SuppressWarnings("unchecked")
		AnnotatedModel meta = new AnnotatedModel((Class<? extends EntityObject>) cls);
		Object[] keys = (Object[]) meta.getPrimaryKeys().toArray();
		SQLWrapper wrapper = SQLWrapper.instance();
		wrapper.selectAll().setModel(meta.getTableName()).where();
		for(int i=0,size=id.length; i<size; i++){
			wrapper.eq(keys[i], id[i]);
		}
		this.query = wrapper.getQuery();
		List<T> list = getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
		return list.get(0);
	}
	
	@Override
	public T findOneByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		return getJdbcTemplate().queryForObject(this.query, cls);
	}

	@Override
	public List<T> findAll() {
		this.query = SQLWrapper.instance().selectAll().setModel(meta.getTableName()).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public List<T> findAllByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public List<T> findAllByWrapperInPage(SQLWrapper sqlWrapper,Pageable pageable) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		List<T> totaList = getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
		pageable.setPageCount(totaList.size());
		this.query = sqlWrapper.setModel(meta.getTableName()).limit(pageable).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public List<T> findAllByWrapperInOrder(SQLWrapper sqlWrapper,Sortable sortable) {
		this.query = sqlWrapper.setModel(meta.getTableName()).orderBy(sortable).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public List<T> findAllByWrapperInPageInOrder(SQLWrapper sqlWrapper,Pageable pageable, Sortable sortable) {
		this.query = sqlWrapper.setModel(meta.getTableName()).orderBy(sortable).limit(pageable).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public List<T> findByNativeQuery(String sql) {
		this.query = sql;
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls));
	}
	
	@Override
	public long getCount() {
		this.query = SQLWrapper.instance().selectAll().setModel(meta.getTableName()).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls)).size();
	}

	@Override
	public long getCountByWrapper(SQLWrapper sqlWrapper) {
		this.query = sqlWrapper.setModel(meta.getTableName()).getQuery();
		return getJdbcTemplate().query(this.query, BeanPropertyRowMapper.newInstance(cls)).size();
	}

	
	/*
	@SuppressWarnings("unchecked")
	private T clearEntityObject(T param){
		EntityObject entity = (EntityObject)param;
		String fieldName;
		try {
			if(entity!=null){
				Method[] methods = entity.getClass().getDeclaredMethods();
				Method setMethod;
				for(int i=0;i<methods.length;i++){
				  if(methods[i].getName().startsWith("get")){
					  fieldName = methods[i].getName().substring(3);  // 属性
					  Object value = methods[i].invoke(entity, (Object[])null);
					  System.out.println("set"+fieldName);
					  System.err.println("value:"+value);
					  if(value == null){
						  String entityField = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
						  Field field = entity.getClass().getDeclaredField(entityField);
						  setMethod = entity.getClass().getDeclaredMethod("set"+fieldName,field.getType());
						  //System.out.println("type:"+field.getType());
						  //Object defaultValue = new Object();
						  String fieldType = field.getType().getSimpleName();
						  System.out.println("fieldType :" +fieldType);
						  if(fieldType.equals("String")){
							  
						  }else if(fieldType.equals("Number")){
							  
						  }else if(fieldType.equals("Date")){
							  
						  }
					  }
				  }
				}
			}
			System.out.println(entity.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (T) entity;
	}
	*/
	


}