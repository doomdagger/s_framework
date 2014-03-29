package com.hg.ecommerce.dao.impl;

import java.lang.reflect.ParameterizedType;


public class BaseDaoImpl<T>{
	
	private Class<T> cls;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		this.cls =(Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	


}
