package com.hg.ecommerce.test;

import org.junit.Test;

import com.hg.ecommerce.config.ProjectConfig;

public class DemoUnitTest {
	@Test
	public void demoTest(){
		System.err.println(ProjectConfig.getProperty("fake.path"));;
	}
}
