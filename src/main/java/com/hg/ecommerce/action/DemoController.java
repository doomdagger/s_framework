package com.hg.ecommerce.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hg.ecommerce.service.DemoService;
import com.hg.ecommerce.util.Util;

@Controller
@RequestMapping("/") //默认值，将 自己的  namespace 追加在Controller中的全部@RequestMapping之前
public class DemoController {

	@Autowired
	private DemoService demoService;
	
	/**
	 * empty route
	 * @return
	 */
	@RequestMapping("/index") // left equal to @RequestMapping(value="/index", method={RequestMethod.GET,RequestMethod.POST})
	public String index(){
		return "main";
	}
	
	/**
	 * map more than one route
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/model","/entity"}, method=RequestMethod.GET)
	public String model(Model model){
		model.addAttribute("message", demoService.greeting("Hi! Nice to see you"));
		return "main";
	}
	
	
	/**
	 * URL template pattern
	 * @param day
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/{day}", method = RequestMethod.GET)
	public String getForDay(@PathVariable("day") @DateTimeFormat(pattern="yyyy-MM-dd") Date theDay, Model model) {
	   model.addAttribute("date",Util.dateToString(theDay));
		return "main";
	}
	 
	/**
	 * URL template pattern -- populate with map
	 * @param day
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/{username}/{age}", method = RequestMethod.GET)
	public String map(@PathVariable Map<String, String> pathVarMap, Model model) {
	   model.addAllAttributes(pathVarMap);
		return "main";
	}
	
	/**
	 * URL template pattern -- Work with matrix
	 * GET /teacher/1018110323;name=lihe;age=18
	 * @param day
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/teacher/{userId}", method = RequestMethod.GET)
	public String matrix(@PathVariable String userId
			, @MatrixVariable(value="name",defaultValue="joe") String theName
			, @MatrixVariable int age, Model model) {
	   model.addAttribute("username",theName);
	   model.addAttribute("age",age);
		return "main";
	}
	
	/**
	 * URL template pattern -- Work with matrix -- advanced
	 * matrix can also be obtained in a map.
	 * 
	 * GET /teacher/1018110323;name=lihe/18;fakeAge=25
	 * @param day
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/teacher/{userId}/{age}", method = RequestMethod.GET)
	public String matrixAdvanced(@PathVariable String userId
			, @PathVariable int age
			, @MatrixVariable(value="name", pathVar="userId") String theName
			, @MatrixVariable(pathVar="age") int fakeAge, Model model) {
	   model.addAttribute("username",theName);
	   model.addAttribute("age",fakeAge);
		return "main";
	}
	
	/**
	 * handle request param -- 处理GET形式的请求参数, 同样可以用一个Map填充
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/request",method=RequestMethod.GET)
	public String dealParam(@RequestParam("name") String theName, @RequestParam("age") int age, Model model){
		model.addAttribute("username",theName);
		model.addAttribute("age",age);
		return "main";
	}
	
	/**
	 * handle request body -- 处理POST形式的请求体
	 * @param body
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String handle(@RequestBody Map<String, String> body, Model model) throws IOException {
	    model.addAllAttributes(body);
	    return "main";
	}

	/**
	 * want to have more control --- ok!
	 * @param requestEntity
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/something")
	public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) throws
	 UnsupportedEncodingException {
	    String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader");
	    byte[] requestBody = requestEntity.getBody();
	    System.err.println(requestHeader+"\n"+requestBody);
	    // do something with request header and body
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("MyResponseHeader", "MyValue");
	    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
	}

	
	
}
