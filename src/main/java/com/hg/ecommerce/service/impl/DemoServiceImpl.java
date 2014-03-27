package com.hg.ecommerce.service.impl;

import org.springframework.stereotype.Component;

import com.hg.ecommerce.service.DemoService;

@Component("demoService")
public class DemoServiceImpl implements DemoService{

	@Override
	public String greeting(String salutation) {
		return "Lihe Say: " + salutation;
	}

	@Override
	public boolean areYouGood() {
		return true;
	}

}
