package com.hg.ecommerce.test;


import org.junit.Test;

import com.hg.ecommerce.dao.impl.BaseDaoImpl;
import com.hg.ecommerce.dao.support.SQLWrapper;

public class DemoBaseDaoImpl {
	BaseDaoImpl<TestModel> dao = new BaseDaoImpl<TestModel>();
	SQLWrapper wrapper = SQLWrapper.instance();
	
	
	//insert model
	@Test
	public void test1(){
		TestModel model = new TestModel();
		model.setName("JOECHOW");
		model.setGender("Male");
		model.setAge("18");
		wrapper.insert(model);
		System.out.println("TEST-1: "+wrapper.getQuery());
	}
	
	@Test
	public void test2(){
		Object[] objects = new Object[]{"HEIGHT","WEIGHT"};
		Object[] values = new Object[]{150,180};
		wrapper.insert().fields("NAME").fields("AGE").fields(objects).values("JOECHOW").values(18).values(values);
		System.out.println("TEST-2: "+wrapper.getQuery());
	}

}
