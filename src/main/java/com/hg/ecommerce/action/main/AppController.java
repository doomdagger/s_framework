package com.hg.ecommerce.action.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.ecommerce.action.extension.editor.SysSettingEditor;
import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.model.SysSetting;

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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(SysSetting.class, new SysSettingEditor());
	}
	
	@RequestMapping("/parse")
	public void parse(@RequestParam SysSetting setting, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		
		PrintWriter writer = response.getWriter();
		
		writer.println(setting.toJSON());
		writer.flush();
		writer.close();
	}
		
	
	/**
	 * 注意，formatter的优先级低于editor，如果同时存在对日期的formatter和editor，editor的策略会被使用。
	 * @param date
	 * @return
	 */
	@RequestMapping("/test")
	@DateTimeFormat(pattern="yyyy/MM/dd")
	@ResponseBody
	public String test(@RequestParam Date date){
		return date.toString();
	}
}
