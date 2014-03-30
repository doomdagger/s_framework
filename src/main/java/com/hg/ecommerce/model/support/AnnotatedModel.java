package com.hg.ecommerce.model.support;

public interface AnnotatedModel {
	String getTableName();
	
	String getColumnName(String fieldName);
}
