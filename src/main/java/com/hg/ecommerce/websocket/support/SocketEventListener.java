package com.hg.ecommerce.websocket.support;

/**
 * 实现此接口来执行业务逻辑
 * @author Li He
 *
 */
public interface SocketEventListener {
	
	/**
	 * 此方法是用户需要实现的唯一方法，当此时间触发时，用户希望获取的行为是什么，
	 * 在方法中指定实现。
	 */
	void actionPerformed(SocketEvent event);
	
}
