package com.hg.ecommerce.config;

import com.google.inject.AbstractModule;
import com.hg.ecommerce.model.support.FieldTypeMapper;

/**
 * 在程序运行时动态绑定接口与实现类，免去冗余配置信息修改
 * @author lihe
 *
 */
public class ProjectContainer extends AbstractModule{

	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		//get db name
		String dbName = ProjectConfig.getProperty("db.name");
		//get mapper class
		String fieldTypeMapperCls = ProjectConfig.getProperty(dbName+".field");
		//get sql dialect class
		String sqlDialectCls = ProjectConfig.getProperty(dbName+".sql");
		
		if(dbName==null||fieldTypeMapperCls==null||sqlDialectCls==null){
			throw new RuntimeException("Unhandled Provider, You should at least choose One provider for your dbms. Check the properties config file exists?");
		}
		
		try {
			bind(FieldTypeMapper.class).to((Class<? extends FieldTypeMapper>)Class.forName(fieldTypeMapperCls));
			//joe, please insert your bind config here...
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
