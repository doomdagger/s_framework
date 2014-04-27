package com.hg.ecommerce.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import com.hg.ecommerce.websocket.support.SocketEvent;
import com.hg.ecommerce.websocket.support.SocketEventListener;


/**
 * 使用此类为不同的SocketEvent注册监听器，时间触发后，监听器会自动执行方法，完成用户所实现的业务逻辑
 * @author Li He
 * @see com.hg.ecommerce.websocket.support.SocketEvent
 * @see com.hg.ecommerce.websocket.support.SocketEventListener
 */
public class Socket {
	
	private Map<String, List<SocketEventListener>> hub = new HashMap<String, List<SocketEventListener>>();

	private WebSocketSession session;
	

	public Socket(WebSocketSession session) {
		this.session = session;
	}

	public Socket on(String event, SocketEventListener listener){
		
		List<SocketEventListener> temp = getHub().get(event);
		
		if(temp==null){
			temp = new ArrayList<SocketEventListener>();
		}
		
		temp.add(listener);
		
		getHub().put(event, temp);
		
		
		return this;
	}
	
	/**
	 * Get all the listeners on the specific event
	 * @param event
	 * @return
	 */
	protected List<SocketEventListener> getListeners(String event){
		
		return getHub().get(event);
		
	}
	
	/**
	 * 检测某一事件是否注册监听器
	 * @param event event name
	 * @return Boolean value
	 */
	protected boolean hasListeners(String event){
		
		if(getListeners(event)==null||getListeners(event).size()==0){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 执行某一注册事件的全部监听器
	 * @param event 事件名称
	 */
	public boolean performEvent(String name, SocketEvent event){
		
		if(hasListeners(name)){
			List<SocketEventListener> listeners = getListeners(name);
			
			for(SocketEventListener listener : listeners){
				
				listener.actionPerformed(event);
				
			}
			
			return true;
		}
		
		return false;
	}
	

	/**
	 * Get Session Id
	 * @return
	 */
	public String getSessionId(){
		return session.getId();
	}
	
	
	/**
	 * socket is active or not?
	 * @return boolean value
	 */
	public boolean isActive(){
		return session.isOpen();
	}
	
	/**
	 * close the under session
	 * @throws IOException exception
	 */
	public void close() throws IOException{
		session.close();
	}
	
	/**
	 * close the under session
	 * @throws IOException exception
	 */
	public void close(CloseStatus status) throws IOException{
		session.close(status);
	}
	
	
	/**
	 * 触发前端事件，仅仅以字符串形式触发
	 * @param event
	 * @param message
	 * @return
	 */
	public Socket emit(String event, String message){
		//TODO the passing message and the operation of the method
		return this;
	}
	
	/**
	 * Get Hub Object
	 * @return Map
	 */
	public Map<String, List<SocketEventListener>> getHub() {
		return hub;
	}

	
	
	
	
		 
	
}
