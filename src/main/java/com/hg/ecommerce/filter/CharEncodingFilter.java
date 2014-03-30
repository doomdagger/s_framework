package com.hg.ecommerce.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class CharEncodingFilter implements Filter {
        
    /**
     * init
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    /**
     * destroy
     */
    public void destroy() {
    }
    
    /**
     * Set the character encoding and sync up Struts and JSTL locales.  This filter should normally be first (and last)
     * in the chain.
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
        
        try {
        	if (!"UTF-8".equals(req.getCharacterEncoding())) {
        		// only set encoding if not already UTF-8
        		// despite the fact that this is the first filter in the chain, on Glassfish it 
        		// is already too late to set request encoding without getting a WARN level log message
        		req.setCharacterEncoding("UTF-8");
        	}
            
        } catch (UnsupportedEncodingException e) {
            // This should never happen since UTF-8 is a Java-specified required encoding.
            throw new ServletException("Can't set incoming encoding to UTF-8");
        }
        
        chain.doFilter(req, res);
    }
    
}