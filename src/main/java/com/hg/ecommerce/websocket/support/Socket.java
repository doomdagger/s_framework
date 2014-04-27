package com.hg.ecommerce.websocket.support;

import static com.hg.ecommerce.websocket.support.BasicType.*;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.websocket.exception.BadMessageException;
import com.hg.ecommerce.websocket.exception.NoGenericSuperInterfaceException;


/**
 * 使用此类为不同的SocketEvent注册监听器，时间触发后，监听器会自动执行方法，完成用户所实现的业务逻辑
 * @author Li He
 * @see com.hg.ecommerce.websocket.support.SocketEvent
 * @see com.hg.ecommerce.websocket.support.SocketEventListener
 */
@SuppressWarnings("rawtypes")
public class Socket {
	
	/**
	 * all the socket event listeners bind to the specific event name
	 */
	private Map<String, List<SocketEventListener>> hub = new HashMap<String, List<SocketEventListener>>();

	/**
	 * all the attributes for the socket
	 */
	private Map<String, String> attributes = new HashMap<String, String>(); 
	
	/**
	 * core socket session
	 */
	private WebSocketSession session;
	

	public Socket(WebSocketSession session) {
		this.session = session;
	}

	/**
	 * bind a listener to a event here
	 * @param event event name
	 * @param listener listener class
	 * @return
	 */
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
	 * 执行某一注册事件的全部监听器, 为同一事件注册多个监听器时，同一个事件的监听器消耗的消息必须为同一类型，否则后果无法预料
	 * @param event 事件名称
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public <T> boolean performEvent(String name, String rawMessage) throws NoGenericSuperInterfaceException,BadMessageException{
				
		if(hasListeners(name)){
			List<SocketEventListener> listeners = getListeners(name);
			
			for(SocketEventListener listener : listeners){
				
				Class<T> cls = guessCls(listener);
				
				//I will handle most basic type
				try {
					T message;
					if(STRING.equals(cls.getName())){
						message = (T)rawMessage;
					}else{
						message = ProjectContainer.getInstance(ObjectMapper.class).readValue(rawMessage, cls);
					}
										
					listener.actionPerformed(message);

				} catch (IOException e) {
					throw new BadMessageException("Bad Message, Cannot read Instance of Class:"+cls.getName()+" from the given raw message", e);
				}
				
			}
			
			return true;
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> guessCls(SocketEventListener<T> listener) throws NoGenericSuperInterfaceException{
		
		
		Type type = listener.getClass().getGenericInterfaces()[0];
		
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)type;
			
			return (Class<T>)parameterizedType.getActualTypeArguments()[0];
			
		}else{
			//父接口没有使用任何的泛型，默认使用Object.class
			throw new NoGenericSuperInterfaceException("The anonymous SocketEventListener is not derived from an interface with the specified Generic Type");
		}
		
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
	 * 触发前端事件，以对象形式触发
	 * @param event
	 * @param message
	 * @return for chain invocation
	 * @throws IOException send message exception
	 */
	public Socket emit(String event, Object message) {
		
		ObjectMapper mapper = ProjectContainer.getInstance(ObjectMapper.class);

		SocketMessage socketMessage = new SocketMessage();
		
		socketMessage.setEventName(event);
		
		try {
			if(message instanceof String){
				socketMessage.setPayload((String)message);
			}else{
				socketMessage.setPayload(mapper.writeValueAsString(message));			
			}
		} catch (JsonProcessingException e) {
			//throw new BadMessageException("The Given Message Object cannot be write to JSON String",e);
			e.printStackTrace();
		}
		
		try {
			TextMessage textMessage = new TextMessage(mapper.writeValueAsString(socketMessage));
			
			session.sendMessage(textMessage);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * Get Hub Object
	 * @return Map
	 */
	public Map<String, List<SocketEventListener>> getHub() {
		return hub;
	}
	
	
	public Socket setAttribute(String name, String value){
		attributes.put(name, value);
		
		return this;
	}
	
	public String getAttribute(String name){
		
		return attributes.get(name);
		
	}
	
	
	
}
