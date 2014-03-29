package com.hg.ecommerce.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.hg.ecommerce.dao.support.provider.MySQLProvider;

public class MysqlProviderTest {

	MySQLProvider provider = new MySQLProvider();
	
	@Test
	public void test() {
		//select();
		update();
	}
	
	public void select(){
		List<Object> objects = new ArrayList<Object>();
		objects.add("NAME");
		objects.add("JOECHOW");
		objects.add("AGE");
		
		provider.select().fields(objects).where().eq("NAME", "joechow");
		provider.setModel("Model");
		System.out.println(provider.getSQL());
	}
	
	public void update(){
		provider.update().where().eq("AGE", 18).and((new MySQLProvider()).inc("MONEY", 8000));
		provider.setModel("Model");
		System.out.println(provider.getSQL());
	}

}
