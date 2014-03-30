package com.hg.ecommerce.model.support;

import java.util.Set;

public interface AnnotatedModel {
	String getTableName();
	
	String getColumnName(String fieldName);
	
	Set<String> getPrimaryKeys();
}
