package com.hg.ecommerce.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hg.ecommerce.dao.support.projection.MySQLProjections;
import com.hg.ecommerce.dao.support.provider.MySQLProvider;

public class MysqlProviderTest {

	MySQLProvider provider = new MySQLProvider();
	
	@Test
	public void test() {
		//select();
		//update();
	}
	@Test
	public void select(){
		List<Object> objects = new ArrayList<Object>();
		objects.add("Host");
		objects.add("User");
		objects.add("Password");
		//select action
		provider.select((new MySQLProjections()))
		.where()
		.eq("User", "root");
		provider.setModel("user");
		System.out.println(provider.getSQL());
	}
	
	public void update(){
		provider.update().where().eq("AGE", 18).and((new MySQLProvider()).inc("MONEY", 8000));
		provider.setModel("Model");
		System.out.println(provider.getSQL());
	}

}
