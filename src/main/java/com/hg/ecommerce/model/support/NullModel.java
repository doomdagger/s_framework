package com.hg.ecommerce.model.support;

public class NullModel extends EntityObject{

	/**
	 * serial version uuid
	 */
	private static final long serialVersionUID = -7345931129556910218L;

	private String name;
	private String message;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
