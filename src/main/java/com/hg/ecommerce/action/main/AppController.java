package com.hg.ecommerce.action.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hg.ecommerce.config.ProjectConfig;

@Controller
@RequestMapping("/")
public class AppController {
	
	@RequestMapping(value="/app/index",produces="text/html")
	public String index(Model model){
		String extRoot = ProjectConfig.getProperty("ext.url");
		if(!extRoot.endsWith("/")){
			extRoot+="/";
		}
		model.addAttribute("extRoot", extRoot);
		return "index";
	}
		
}
