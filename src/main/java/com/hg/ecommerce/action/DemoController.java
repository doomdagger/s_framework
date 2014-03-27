package com.hg.ecommerce.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hg.ecommerce.service.DemoService;

@Controller
@RequestMapping("/")
public class DemoController {

	@Autowired
	private DemoService demoService;
	
	@RequestMapping("/index.action")
	public String index(){
		System.err.println(demoService.greeting("Hello~!"));
		demoService.areYouGood();
		return "/main";
	}
}
