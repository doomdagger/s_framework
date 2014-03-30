package com.hg.ecommerce.model.support;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.hg.ecommerce.model.support.annotation.Column;
import com.hg.ecommerce.model.support.annotation.Id;
import com.hg.ecommerce.model.support.annotation.Table;

public class AnnotatedModel<T> {
	
	private Class<T> cls;
	
	public AnnotatedModel(Class<T> cls){
		this.cls = cls;
	}
	
	/**
	 * 获取 实体 所映射的表名
	 * @return
	 */
	public String getTableName() {
		
		if(cls.isAnnotationPresent(Table.class)){
			return cls.getAnnotation(Table.class).value();
		}
		
		return "";
	}

	/**
	 * 获取对应 字段 所映射的列名
	 * @param fieldName
	 * @return
	 */
	public String getColumnName(String fieldName) {
		try {
			Field field = cls.getDeclaredField(fieldName);
			if(field.isAnnotationPresent(Column.class)){
				return field.getAnnotation(Column.class).value();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public Set<String> getPrimaryKeys() {
		Set<String> ids = new HashSet<String>();
		
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)){
				ids.add(getColumnName(field.getName()));
			}
		}
		return ids;
	}
	
}
