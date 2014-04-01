package com.hg.ecommerce.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * 拦截器为开发者提供了3个时间点，以便介入一个请求的生命周期，在这期间，用户可以通过HttpServletResponse改变
 * 响应的方式，这将改变request的原有生命周期，更重要的是，可以增加逻辑来对请求进行额外的控制
 * </p>
 * <p>
 * 拦截器需要在XML配置文件中进行配置，可以使用java config避免，但是无法通过annotation来避免
 * 详情见action-serlvet.xml
 * </p>
 * @author Li He
 *
 */
public class UserControlInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger mLogger = LogManager.getLogger(UserControlInterceptor.class);
	
	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		
		mLogger.trace("Pre Handle");
		//want to revert the request, please modify return true to return false
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		mLogger.trace("Post Handle");
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		mLogger.trace("Already Complete!");
	}

	
}
