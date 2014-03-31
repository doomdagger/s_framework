package com.hg.ecommerce.model.test;

import com.hg.ecommerce.model.support.EntityObject;
import com.hg.ecommerce.model.support.annotation.Column;
import com.hg.ecommerce.model.support.annotation.Id;
import com.hg.ecommerce.model.support.annotation.Table;

@Table("test_model")
public class TestModel extends EntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8787908422983285410L;
	
	@Id
	@Column("id")
	private String id;
	
	@Column("name")
	private String name;
	
	@Column("age")
	private int age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
