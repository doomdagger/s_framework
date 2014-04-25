package com.hg.ecommerce.service.rmi.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

import com.hg.ecommerce.service.rmi.RMITestService;

/**
 * Client cannot see Raw Servlet Parameters
 * @author Li He
 *
 */
@Service("rmiTestService")
public class RMITestServiceImpl implements RMITestService{

	@Override
	@ExtDirectMethod
	public long longValueRet(long num) {
		// TODO Auto-generated method stub
		return num;
	}

	@Override
	@ExtDirectMethod
	public String stringValueRet(String str) {
		// TODO Auto-generated method stub
		return str;
	}

	@Override
	@ExtDirectMethod
	public String accessRequest(HttpServletRequest rawRequest) {
		// TODO Auto-generated method stub
		return "Hello";
	}

	@Override
	@ExtDirectMethod
	public boolean accessResponse(HttpServletResponse rawResponse) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@ExtDirectMethod
	public double accessSession(HttpSession rawSession) {
		// TODO Auto-generated method stub
		return 20;
	}
	
	
	
}
