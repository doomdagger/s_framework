package com.hg.ecommerce.service.rmi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public interface RMITestService {
	
	long longValueRet(long num);
	
	String stringValueRet(String str);

	String accessRequest(HttpServletRequest rawRequest);
	
	boolean accessResponse(HttpServletResponse rawResponse);
	
	double accessSession(HttpSession rawSession);
}
