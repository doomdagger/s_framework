package com.hg.ecommerce.action.demo;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

import com.hg.ecommerce.action.extension.MIME;
import com.hg.ecommerce.model.SysSetting;
import com.hg.ecommerce.util.Util;

/**
 * restController, give me some rest!
 * <p>take advantage of restful coding style</p>
 * @author lihe
 *
 */
@RestController
@RequestMapping("/demo/webservice")
public class RestfulController {
	
	/**
	 * 为了返回JSON字符串，你可以使用framework提供的getJsonObject方法，将一个model序列化为
	 * 一个JSONObject，而后以字符串型式返回。
	 * @param pathVar
	 * @return
	 */
	@RequestMapping(value="/json/{name}/{age}",produces="application/json")
	public String getJsonResult(@PathVariable Map<String, String> pathVar){
		return Util.getJsonObject(pathVar).toString();
	}
	
	/**
	 * 测试当抛出错误时，ControllerAdvice配置是否可以生效
	 * @param pathVar
	 * @return
	 */
	@RequestMapping(value="/exception",produces="application/json")
	public String testException(){
		throw new NullPointerException("Error, I've made it.");
	}
	
	/**
	 * Spring提供了很多内置的Converter，他们比Editor使用起来更方便，而且提供全局性的类型转换，
	 * 接下来的多个@RequestMapping将用来展示几个常用的Converter类，当然，你也可以写自己的Converter
	 * 功能类，com.hg.ecommerce.action.extension.converter包中有converter的实现例子.
	 * 
	 * <p>不太推荐通过自定义Convertor来实现Model转型，对于通用的Model处理，框架本身提供的Util方法，以及
	 * Spring 提供的JSON Converter已经足够用，对于一些Model的入参转型，推荐使用Property Editor这种
	 * 轻量级的转型策略</p>
	 * 
	 * <p>当使用@ResponseBody返回一个Object，或者使用@RequestBody获取一个Object的时候，
	 * 如果没有定义相应的Editor，那么Convertor就会生效，这里自己生效的就是<br>
	 * <code>org.springframework.http.converter.json.MappingJackson2HttpMessageConverter</code>,
	 * 此converter可以用来序列化对象，并生成JSON字符串</p>
	 * @param message
	 * @return
	 * 
	 * @see org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
	 */
	@RequestMapping("/converter/json")
	public SysSetting json(@RequestParam String message){
		SysSetting setting = new SysSetting();
		setting.setPropId(12);
		setting.setEdittime(new Date());
		setting.setPropKey("大家好~");
		setting.setPropValue("Good Morning!");
		return setting;
	}
	
	/**
	 * <p>使用Resource Converter来处理Media Type文件的上传和下载，如果是上传，可以在传参中添加
	 * @RequestBody注解，参数同样为Resource类型，Spring MVC会为你自动转型</p>
	 * 
	 * <p>切记，请选好合适的MIME Type，不然浏览器无法解析正确</p>
	 * @return
	 */
	@RequestMapping(value="/converter/resource",produces=MIME.JPEG)
	public Resource resource(HttpServletRequest request){
		//Resource resource = new ClassPathResource("project-custom.properties");
		Resource resource = new ServletContextResource(request.getServletContext(), "/app/wallpapers/london.jpg");
		return resource;
	}
	
	
	/**
	 * <p>form data parsing use FormHttpMessageConverter class</p>
	 */
	@RequestMapping(value="/converter/form")
	public MultiValueMap<String, String> form(@RequestBody MultiValueMap<String, String> form){
		return form;
	}
} 
