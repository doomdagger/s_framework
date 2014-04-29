package com.hg.ecommerce.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.dao.support.UUIDGenerator;
import com.hg.ecommerce.websocket.support.Socket;
import com.hg.ecommerce.websocket.support.SocketMessage;

/**
 * ConnectHub, 在这里存放一切的连接对象
 * @author Li He
 *
 */
public class ConnectionHub {

	public final static String uuid = UUIDGenerator.randomUUID();
	
	private final static ConnectionHub connectionHub = new ConnectionHub();
	
	
	
	/**
	 * 添加一个连接
	 * @param userId unique user identifier
	 * @param session socket object for WebSocket
	 * @return socket invocation
	 */
	public static Socket cacheSocket(String userId, WebSocketSession session){
		
		Socket socket = new Socket(session);
		
		connectionHub.pool.put(session.getId(), socket);
		connectionHub.user_cache.put(session.getId(), userId);
		
		return socket;
	}
	
	
	/**
	 * 添加一个连接
	 * @param session socket object for WebSocket
	 * @return socket invocation
	 */
	public static Socket cacheSocket(WebSocketSession session){
		
		Socket socket = new Socket(session);
		
		connectionHub.pool.put(session.getId(), socket);
		
		return socket;
	}
	
	/**
	 * 利用session id来获取Socket Object
	 * @param sessionId session id
	 * @return Socket Object
	 */
	public static Socket getSocket(String sessionId){
		
		return connectionHub.pool.get(sessionId);
		
	}
	
	/**
	 * 通过user id这种binding信息，来获取一个Socket对象
	 * @param uid user identifier
	 * @return Socket
	 */
	public static Socket getSocketViaBinding(String uid) {
		
		for(Entry<String, String> entry : connectionHub.user_cache.entrySet()){
			
			if(entry.getValue().equals(uid)){
				return getSocket(entry.getKey());
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * 根据user identifier的集合仅仅获取那些在线的用户的Socket对象，
	 * <strong>当获取这些Socket是为了emit消息时，不推荐使用此方法，因为多次的遍历会显著降低效率</strong>
	 * @param uids 若干个user identifier
	 * @return List -- Socket集合
	 */
	public static List<Socket> getSocketsViaBinding(String... uids){
		
		List<Socket> sockets = new ArrayList<Socket>();
		
		List<String> list = Arrays.asList(uids);
		
		list.retainAll(connectionHub.user_cache.values());
		
		for(String uid : list){
			
			sockets.add(getSocketViaBinding(uid));
			
		}
		
		return sockets;
		
	}
	
	/**
	 * 根据user identifier的集合获取那一部分offline的用户的bindings信息，当用户不在线却想要发送离线消息时，
	 * 可以使用此方法筛选出offline的用户
	 * @param uids user identifier
	 * @return List -- String
	 */
	public static List<String> getOfflineBindings(String... uids){
		
		List<String> list = Arrays.asList(uids);
		
		list.removeAll(connectionHub.user_cache.values());
		
		return list;
	}
	
	/**
	 * 延迟绑定，绑定sessionId 到 一个userId
	 * @param sessionId session id
	 * @param userId user id
	 */
	public static void bindSocket(String sessionId, String userId){
		
		connectionHub.user_cache.put(sessionId, userId);
		
	}
	
	/**
	 * 将TextMessage的raw String解析为PlainTextMessage对象
	 * @param message TextMessage
	 * @return PlainTextMessage
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static SocketMessage transferMessage(TextMessage message) throws IOException {
		
		String payload = message.getPayload();
		
		ObjectMapper mapper = ProjectContainer.getInstance(ObjectMapper.class);
		
		SocketMessage socketMessage = mapper.readValue(payload, SocketMessage.class);
		
		return socketMessage;
		
	}
	
	
	
	
	/**
	 * 删掉不活跃的连接信息
	 * @return 删除掉的连接数量
	 */
	public static int retainActiveSocket(){
		
		int count = 0;
		
		for(Entry<String, Socket> entry : connectionHub.pool.entrySet()){
			if(!entry.getValue().isActive()){
				connectionHub.pool.remove(entry.getKey());
				connectionHub.user_cache.remove(entry.getKey());
				count++;
			}
		}
		
		return count;
		
	}
	
	/**
	 * 删除一个Connection，用户应当自行承担connection是否已经关闭的风险
	 * @param sessionId
	 * @throws IOException 
	 */
	public static void removeSocket(String sessionId){
		
		removeSocket(connectionHub.pool.get(sessionId));
		
	}
	
	/**
	 * safely删除一个Connection
	 * @param session session
	 * @throws IOException session关闭时可能抛出异常
	 */
	public static void removeSocket(Socket socket){
		
		if(socket==null) return;
		
		String sessionId = socket.getSessionId();
		
		try{
			if(socket.isActive()){
				socket.close();
			}
		}catch(IOException exception){
			exception.printStackTrace();
		}finally{
			connectionHub.pool.remove(sessionId);
			connectionHub.user_cache.remove(sessionId);
		}
		
		
	}
	
	/**
	 * return the number of the active socket
	 * @return int value
	 */
	public static int getActiveSocketCount(){
		
		return connectionHub.pool.size();
		
	}
	
	/**
	 * Get All The Sockets Except for the one with passing id
	 * @param sessionId session id
	 * @return collection of sockets
	 */
	public static Collection<Socket> getOtherSockets(String sessionId){
		
		Collection<Socket> sockets = new ArrayList<Socket>();
		
		for(Entry<String, Socket> entry : connectionHub.pool.entrySet()){
			if(entry.getKey().equals(sessionId)){
				continue;
			}
			
			sockets.add(entry.getValue());
		}
		
		return sockets;
	}
	
	/**
	 * 广播对象为全体在线用户
	 * @param event event name
	 * @param message message object
	 */
	public static void emit(String event, Object message){
		
		for(Socket socket : connectionHub.pool.values()){
			
			socket.emit(event, message);
			
		}
		
	}
	
	//*********************************** gorgeous horizontal split ***********************************//
	
	/**
	 * pool's key: session id;
	 * pool's value: Socket object;
	 */
	private Map<String, Socket> pool = new HashMap<String, Socket>();
	
	/**
	 * cache's key: session id;
	 * cache's value: user identifier;
	 */
	private Map<String, String> user_cache = new HashMap<String, String>();
	
	private ConnectionHub() {}
	
	
}
