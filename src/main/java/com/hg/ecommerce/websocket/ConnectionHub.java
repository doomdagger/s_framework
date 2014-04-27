package com.hg.ecommerce.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.hg.ecommerce.websocket.support.PlainTextMessage;

/**
 * ConnectHub, 在这里存放一切的连接对象
 * @author Li He
 *
 */
public class ConnectionHub {

	private static ConnectionHub connectionHub = new ConnectionHub();

	
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
	 */
	public static PlainTextMessage wrapTextMessage(TextMessage message){
		
		//TODO still not clear the operation
		
		return new PlainTextMessage();
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
	 */
	public static void removeSocket(String sessionId){
		
		connectionHub.pool.remove(sessionId);
		connectionHub.user_cache.remove(sessionId);
		
	}
	
	/**
	 * safely删除一个Connection
	 * @param session session
	 * @throws IOException session关闭时可能抛出异常
	 */
	public static void removeSocket(Socket socket) throws IOException{
		
		if(socket.isActive()){
			socket.close();
		}
		
		removeSocket(socket.getSessionId());
		
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
