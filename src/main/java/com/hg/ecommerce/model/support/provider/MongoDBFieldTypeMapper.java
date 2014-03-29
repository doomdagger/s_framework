package com.hg.ecommerce.model.support.provider;

import java.util.List;
import java.util.Map;

import com.hg.ecommerce.model.support.FieldEntry;
import com.hg.ecommerce.model.support.FieldTypeMapper;
import com.hg.ecommerce.model.support.TableDef;

public class MongoDBFieldTypeMapper implements FieldTypeMapper{

	
	@Override
	public Map<TableDef, List<FieldEntry>> fetchDatabaseMeta(String url,
			String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class mapFieldType(FieldEntry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
