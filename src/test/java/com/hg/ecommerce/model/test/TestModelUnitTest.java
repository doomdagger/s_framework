package com.hg.ecommerce.model.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.service.SysSettingService;

public class TestModelUnitTest {
	@Test
	public void testInjection(){
		System.setProperty("log.path", ProjectConfig.getProperty("log.path"));
		
		@SuppressWarnings("resource")
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/applicationContext.xml");
	
		SysSettingService sysSettingService = (SysSettingService)context.getBean("sysSettingService");
	
		SysSetting sysSetting = new SysSetting();
		
		sysSetting.setCreateperson(1);
		sysSetting.setEditperson(1);
		sysSetting.setCreatetime(new Date());
		sysSetting.setEdittime(new Date());
		sysSetting.setPropKey("系统安全级别");
		sysSetting.setPropValue("高级");
		sysSetting.setRemark("在此安全级别下，任何未授权访问都会被拒绝。");
		
		sysSettingService.save(sysSetting);
		
		System.err.println(sysSettingService.selectAll());
				
	}
	
	
}
