package com.hg.ecommerce.model.support;

import java.util.List;
import java.util.Map;

/**
 * Which implementation should FieldTypeMapper bind to?
 * handle it to guice. Spring cannot achieve that
 * @author Li He
 *
 */
public interface FieldTypeMapper {
	//映射字段类型
	public <T> Class<T> mapFieldType(FieldEntry entry);
	
	//获取全部表(集合)的全部字段结构
	public Map<TableDef, List<FieldEntry>> fetchDatabaseMeta(String url, String username, String password);
}
