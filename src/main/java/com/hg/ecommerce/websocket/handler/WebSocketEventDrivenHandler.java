package com.hg.ecommerce.websocket.handler;

import java.io.IOException;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.hg.ecommerce.websocket.ConnectionHub;
import com.hg.ecommerce.websocket.Socket;
import com.hg.ecommerce.websocket.support.SocketEvent;

/**
 * WebSocket Event Driven Handler, all the event-driven handler should
 * derived from this one
 * @author Li He
 *
 */
public abstract class WebSocketEventDrivenHandler extends AbstractWebSocketHandler{
	
	/**
	 * Any Derived Class implement this method to bind events to the given socket object
	 * @param socket
	 */
	public abstract void config(Socket socket);
	
	
	/**
	 * 连接成功建立时此方法被调用
	 * 将session添加到ConnectionHub中，并检测用户是否注册connection事件，如果注册了，触发此事件
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		Socket socket = ConnectionHub.cacheSocket(session);
		
		SocketEvent event = new SocketEvent("connection");
		
		socket.performEvent("connection", event);
	}
	
	
	/**
	 * 连接结束时，此方法调用
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO do something here
	}
	
	/**
	 * 消息到来时，此方法被调用
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		session.sendMessage(new TextMessage("Hi! You Back"));
	
	}
	
	/**
	 * 二进制消息到来时，此方法被调用
	 */
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
	
	}

	/**
	 * 异常被触发时，此方法调用
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		
	}

	
}
