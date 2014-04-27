package com.hg.ecommerce.websocket.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.websocket.WebSocketEventDrivenHandler;
import com.hg.ecommerce.websocket.exception.BadMessageException;
import com.hg.ecommerce.websocket.support.Socket;
import com.hg.ecommerce.websocket.support.SocketEventListener;

/**
 * Handle Incoming Message and Manage WebSocket Session object
 * @author Li He
 * @see com.hg.ecommerce.websocket.ConnectionHub
 */
@Component("messageHandler")
public class MessageHandler extends WebSocketEventDrivenHandler{

	/**
	 * 使用此方法注册事件，并执行连接建立后的部分逻辑
	 * @throws IOException 
	 * @throws BadMessageException 
	 */
	@Override
	public void onConnection(final Socket socket) {
		
		socket.emit("online", "haha");
		
		socket.on("say", new SocketEventListener<String>() {
			@Override
			public void actionPerformed(String message) {
				socket.emit("say", "do not say anything");
			}
		});
		
		socket.on("yell", new SocketEventListener<String>() {

			@Override
			public void actionPerformed(String message) {
				SysSetting setting = new SysSetting();
				setting.setPropId(1234);
				setting.setPropKey("嘿嘿");
				setting.setPropValue("good");
				setting.setRemark("This is the remark field");
				socket.emit("say", setting);
			}
		});
		
	}
	
	
	
}
