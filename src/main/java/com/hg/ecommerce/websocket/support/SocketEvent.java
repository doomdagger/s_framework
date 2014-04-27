package com.hg.ecommerce.websocket.support;


/**
 * Socket Event Object 
 * @author Li He
 */
public class SocketEvent {
	
	private String name;

	private String payload;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SocketEvent(String name){
		this.name = name;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
}
