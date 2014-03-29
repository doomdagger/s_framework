package com.hg.ecommerce.filter;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StructuredDataMessage;
 
public class RequestFilter implements Filter {
    @SuppressWarnings("unused")
	private FilterConfig filterConfig;
    private static String TZ_NAME = "timezoneOffset";
 
    private static Logger logger = LogManager.getLogger(RequestFilter.class);
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
 
    /**
     *  filter that populates the MDC on every request.
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        StructuredDataMessage msg = new StructuredDataMessage("", "", "request trace");
    	
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        msg.put("ipAddress", request.getRemoteAddr());
        HttpSession session = request.getSession(false);
        TimeZone timeZone = null;
        if (session != null) {
           
            // This assumes there is some javascript on the user's page to create the cookie.
            if (session.getAttribute(TZ_NAME) == null) {
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if (TZ_NAME.equals(cookie.getName())) {
                            int tzOffsetMinutes = Integer.parseInt(cookie.getValue());
                            timeZone = TimeZone.getTimeZone("GMT");
                            timeZone.setRawOffset((int)(tzOffsetMinutes * DateUtils.MILLIS_PER_MINUTE));
                            request.getSession().setAttribute(TZ_NAME, tzOffsetMinutes);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        msg.put("hostname", servletRequest.getServerName());
        msg.put("locale", servletRequest.getLocale().getDisplayName());
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        msg.put("timezone", timeZone.getDisplayName());
        filterChain.doFilter(servletRequest, servletResponse);
        
        logger.trace(msg);
        
    }
 
    public void destroy() {
    }
}