package com.hg.ecommerce.websocket.exception;


public class BadMessageException extends RuntimeException{

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 705457214662414426L;
	
	public BadMessageException(String message){
		super(message);
	}
	
	public BadMessageException(String message, Throwable throwable){
		super(message, throwable);
	}

}
