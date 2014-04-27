package com.hg.ecommerce.websocket.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * Handle Incoming Message and Manage WebSocket Session object
 * @author Li He
 * @see com.hg.ecommerce.websocket.ConnectionHub
 */
@Component("messageHandler")
public class MessageHandler extends AbstractWebSocketHandler{
	
	/**
	 * 连接成功建立时此方法被调用
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				
		session.sendMessage(new TextMessage("The Connection is Established"));
	}
	
	/**
	 * 消息到来时，此方法被调用
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		session.sendMessage(new TextMessage("Hi! You Back"));
	
	}
	
	/**
	 * 二进制消息到来时，此方法被调用
	 */
	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
	
	}

	/**
	 * 异常被触发时，此方法调用
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		
	}

	/**
	 * 连接结束时，此方法调用
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO do something here
	}
}
