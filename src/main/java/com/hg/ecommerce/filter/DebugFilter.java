package com.hg.ecommerce.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugFilter implements Filter {
    
    private static Logger log = LogManager.getLogger(RequestFilter.class);
    
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        log.info("ENTERING "+request.getRequestURL());
        
        // some info about the request and response
        log.info("Response Object:");
        log.info("   isCommitted = "+response.isCommitted());
        log.info("   bufferSize  = "+response.getBufferSize());
        log.info("");
        
        chain.doFilter(request, response);
        
        log.info("EXITING "+request.getRequestURL());
        
        // some info about the request and response
        log.info("Response Object:");
        log.info("   isCommitted = "+response.isCommitted());
        log.info("   bufferSize  = "+response.getBufferSize());
        log.info("");
    }
    
    
    public void destroy() {}
    
    
    public void init(FilterConfig filterConfig) {}
    
}