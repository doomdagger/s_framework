package com.hg.ecommerce.test;


import org.junit.Test;

import com.hg.ecommerce.dao.impl.BaseDaoImpl;
import com.hg.ecommerce.dao.support.SQLWrapper;
//import com.hg.ecommerce.model.MCities;

public class DemoSQLWrapperTest {
	//BaseDaoImpl<MCities> dao = new BaseDaoImpl<MCities>();
	SQLWrapper wrapper = SQLWrapper.instance();
	
	//insert model
	@Test
	public void test1(){
		/*
		MCities cities = new MCities();
		cities.setFlag(false);
		cities.setNameEn("JOE");
		cities.setNameZh("乔");
		cities.setParentid(0);
		//wrapper.insert(cities);
		dao.add(cities);
		*/
		System.out.println("TEST-1: "+wrapper.getQuery());
	}
	
	@Test
	public void test2(){
		Object[] objects = new Object[]{"HEIGHT","WEIGHT"};
		Object[] values = new Object[]{150,180};
		wrapper.insert().fields("NAME").fields("AGE").fields(objects).values("JOECHOW").values(18).values(values);
		System.out.println("TEST-2: "+wrapper.getQuery());
	}
	
	@Test
	public void test3(){
		wrapper.delete().where().eq("name", "joecow");
		System.out.println("TEST-3:"+wrapper.getQuery());
		
	}

}
