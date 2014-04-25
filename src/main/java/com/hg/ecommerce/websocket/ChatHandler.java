package com.hg.ecommerce.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component("chatHandler")
public class ChatHandler extends TextWebSocketHandler{
	
	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		
	}
	
}
