package com.hg.ecommerce.websocket.exception;

public class NoGenericSuperInterfaceException extends RuntimeException{

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = -579915374633565673L;

	public NoGenericSuperInterfaceException(String message){
		super(message);
	}
	
	public NoGenericSuperInterfaceException(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
