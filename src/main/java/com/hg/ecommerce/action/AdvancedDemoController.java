package com.hg.ecommerce.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.ecommerce.model.support.EntityObject;
import com.hg.ecommerce.service.DemoService;

@Controller
@RequestMapping("/advance")
public class AdvancedDemoController {
	
	@Autowired
	private DemoService demoService;
	
	/**
	 * 应当将带有注释 @ModelAttribute 的方法放在放在Controller的最前方，因为无论位置在何处，
	 * 这些方法都会优于任何@RequestMapping方法执行，这些方法执行后返回的对象会存在潜在的Model对象中。
	 * 通过将@ModelAttribute放在入参参数前，来自动再获取这些保存的对象。
	 * <br>
	 * 如果有一种对象的构建方法，会存在于多个@RequestMapping中，请使用此种特性，来减少代码冗余，
	 * 获取Mail对象的方法，见下一方法
	 * @param content
	 * @return
	 */
	@ModelAttribute("mail")
	public Mail fetchMail(@RequestParam("content") String content){
		Mail mail = new Mail();
		mail.setTitle("Greeting");
		mail.setContent(demoService.greeting(content));
		
		return mail;
	}
	
	
	@RequestMapping(value="/mail",produces="application/json")
	@ResponseBody
	public String fetchMail(@ModelAttribute("mail") Mail cacheMail){
		return cacheMail.toJSON().toString();
	}
	
	
	/**
	 * 此实体无任何实际作用，就是为了演示Controller功能
	 * @author lihe
	 */
	class Mail extends EntityObject{
		private static final long serialVersionUID = 1L;
		private String title;
		private String content;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
