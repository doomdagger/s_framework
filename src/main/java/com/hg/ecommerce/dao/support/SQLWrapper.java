package com.hg.ecommerce.dao.support;

import com.hg.ecommerce.dao.support.provider.MySQLProvider;


public class SQLWrapper {
	
	public SQLWrapper fields(){
		return this;
	}
	
	public SQLWrapper set(){
		return this;
	}
	
	public SQLWrapper inc(){
		return this;
	}
	
	public SQLWrapper where(){
		return this;
	}
	
	public SQLWrapper and(){
		return this;
	}
	
	public SQLWrapper or(){
		return this;
	}
	
	public SQLWrapper eq(){
		return this;
	}
	
	public SQLWrapper ne(){
		return this;
	}
	
	public SQLWrapper gt(){
		return this;
	}
	
	public SQLWrapper ge(){
		return this;
	}
	
	public SQLWrapper lt(){
		return this;
	}
	
	public SQLWrapper le(){
		return this;
	}
	
	public SQLWrapper not(){
		return this;
	}
	
	public SQLWrapper between(){
		return this;
	}
	
	public SQLWrapper in(){
		return this;
	}
	
	public SQLWrapper notIn(){
		return this;
	}
	
	public SQLWrapper like(){
		return this;
	}
	
	public SQLWrapper notLike(){
		return this;
	}
	
	public SQLWrapper isNull(){
		return this;
	}
	
	public SQLWrapper isNotNull(){
		return this;
	}
	
	public SQLWrapper orderBy(){
		return this;
	}
	
	public SQLWrapper limit(){
		return this;
	}
	
	public static void main(String args[]) {
		MySQLProvider mySQLProvider = MySQLProvider.instance("TABLE");
		Object[] objects = new Object[]{"hello","world","I'AM","HERE"};
		
		mySQLProvider.update().set("name", "joechow").set("age", 18);
		System.out.println(mySQLProvider.getSQL());
	}
}
