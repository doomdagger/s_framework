package com.hg.ecommerce.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hg.ecommerce.dao.support.IProjections;
import com.hg.ecommerce.dao.support.ISQLProvider;
import com.hg.ecommerce.model.support.FieldTypeMapper;

/**
 * 在程序运行时动态绑定接口与实现类，免去冗余配置信息修改
 * @author lihe
 *
 */
public class ProjectContainer extends AbstractModule{

	/**
	 * build guice Injector, based on current Module 
	 */
	private static Injector injector = Guice.createInjector(new ProjectContainer());
	
	/**
	 * Expose only this method to developer, get Instance by passing the correspondent Interface.
	 * @param cls
	 * @return
	 */
	public static <T> T getInstance(Class<T> cls){
		return injector.getInstance(cls);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		//get db name
		String dbName = ProjectConfig.getProperty("db.name");
		//get mapper class name
		String fieldTypeMapperCls = ProjectConfig.getProperty(dbName+".field");
		//get sql dialect class name
		String sqlDialectCls = ProjectConfig.getProperty(dbName+".sql");
		//get projection class name
		String sqlProjections = ProjectConfig.getProperty(dbName+".projection");
		
		/**
		 * No Handler, We do not allow this.
		 */
		if(dbName==null||fieldTypeMapperCls==null||sqlDialectCls==null||sqlProjections==null){
			throw new RuntimeException("Unhandled Provider, You should at least choose One provider for your dbms. Check the properties config file exists?");
		}
		
		/**
		 * Real bind start here, wrap it with exceptions
		 */
		try {
			//dynamic bind
			bind(FieldTypeMapper.class).to((Class<? extends FieldTypeMapper>)Class.forName(fieldTypeMapperCls));
			bind(ISQLProvider.class).to((Class<? extends ISQLProvider>)Class.forName(sqlDialectCls));		
			bind(IProjections.class).to((Class<? extends IProjections>)Class.forName(sqlProjections));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
