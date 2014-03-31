package com.hg.ecommerce.action.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.ecommerce.service.DemoService;

@Controller
@RequestMapping("/demo/advance")
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
	 * @throws UnsupportedEncodingException 
	 */
	@ModelAttribute("mail")
	public void fetchMail(@RequestParam(value="content",required=false,defaultValue="Hello, I am from Dalian") String content, Model model) throws UnsupportedEncodingException {
		Mail mail = new Mail();
		mail.setTitle("Greeting");
		mail.setContent(demoService.greeting(content));
		model.addAttribute(mail);
	}
	
	/**
	 * 当访问/mail时， fetchMail 会先于 returnMail调用，returnMail可以通过@ModelAttribute获取fetchMail返回的Mail对象，
	 * 通过传BindingResult入参，可以检测任何 TypeConversion是否出错。
	 * @param cacheMail
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/mail",produces="text/plain")
	@ResponseBody
	public String returnMail(@ModelAttribute("mail") Mail cacheMail , BindingResult result) throws UnsupportedEncodingException{
		
		if(result.hasErrors()){
			cacheMail.setContent("Error!");
		}
				
		return cacheMail.getTitle()+"\n"+cacheMail.getContent();
	}
	
	/**
	 * 使用原生Servlet Class, 响应信息写入response中，handler方法返回void
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/origin")
	public void useOriginal(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf8");
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute("greeting", demoService.greeting("Hi!"));
		
		PrintWriter writer = response.getWriter();
		
		writer.println("<h1>大家好~</h1>");
		writer.flush();
		
		writer.close();
	}
	
	/**
	 * 使用Stream IO类
	 * @param inputStream
	 * @param outputStream
	 */
	@RequestMapping("/stream")
	public void useStream(InputStream inputStream, OutputStream outputStream){
		PrintWriter writer = new PrintWriter(outputStream);
		
		writer.println("<h1>Hello From Stream</h1>");
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Cookie support
	 * @param cookie
	 */
	@RequestMapping("/displayCookieInfo.do")
	@ResponseBody
	public String displayHeaderInfo(@CookieValue("JSESSIONID") String cookie) {
	    return cookie;
	}

	/**
	 * Header support
	 * @param encoding
	 * @param keepAlive
	 */
	@RequestMapping("/displayHeaderInfo.do")
	@ResponseBody
	public String displayHeaderInfo(@RequestHeader("Accept-Encoding") String[] encoding) {
	    return "<h1>"+Arrays.toString(encoding)+"</h1>";
	}

	/**
	 * @ResponseBody  &   @RequestBody 的说明
	 * <p>RequestBody注解利用HttpMessageConverter的子类将请求体从流中读取后转换为用户索要的类型，这种转换时潜在的，
	 * Spring选择合适的Converter作类型转换，用户直接利用所需的类型进行合适的操作，如果想要实现自定义类型的转换，需要
	 * 自行编写Converter子类，并注册到AnnotationMethodHandlerAdapter中。</p>
	 * 
	 * <p>ResponseBody注解正好是相反的过程，转换后直接写入输出流，跳过view这一部分</p>
	 */
	@RequestMapping(value="/body",produces="text/plain")
	@ResponseBody
	public String useBodyAnnotation(@RequestBody MultiValueMap<String,String> formTrans){
		return formTrans.toString();
	}
	
	/**
	 * 此实体无任何实际作用，就是为了演示Controller功能
	 * @author lihe
	 */
	class Mail {
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
