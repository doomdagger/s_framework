package com.hg.ecommerce.websocket.support;

import java.io.Serializable;

/**
 * Message类, 此类可转化为文本，是真正传输于前端与后端的对象
 * @author Li He
 *
 */
public class SocketMessage implements Serializable{

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2590053637441476430L;
	
	private String eventName;  //事件名称
	
	private String payload; //消息体

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
	
	
}
