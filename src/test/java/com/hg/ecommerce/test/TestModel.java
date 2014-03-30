package com.hg.ecommerce.test;

import com.hg.ecommerce.model.support.EntityObject;

public class TestModel extends EntityObject{
	
	private String name;
	private String gender;
	private String age;
	
	private static final long serialVersionUID = 1L;

	public TestModel(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	

}
