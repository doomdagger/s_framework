package com.hg.ecommerce.action.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
	
	@RequestMapping(value="/app/index",produces="text/html")
	public String index(){
		return "index";
	}
	
}
