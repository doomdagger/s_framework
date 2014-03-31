package com.hg.ecommerce.model.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.dao.impl.TestModelDao;

public class TestModelUnitTest {
	@Test
	public void testInjection(){
		System.setProperty("log.path", ProjectConfig.getProperty("log.path"));
		
		@SuppressWarnings("resource")
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/applicationContext.xml");
	
		TestModelDao testModelDao = (TestModelDao)context.getBean("testModelDao");
	
		System.err.println(testModelDao.getJdbcTemplate()==null);
				
	}
	
	
}
