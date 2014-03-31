package com.hg.ecommerce.action.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hg.ecommerce.util.Util;

/**
 * automatically return json data
 * 
 * @author lihe
 *
 */
@RestController
@RequestMapping("/demo/webservice")
public class RestfulController {
	
	/**
	 * 
	 * @param pathVar
	 * @return
	 */
	@RequestMapping(value="/json/{name}/{age}",produces="application/json")
	public String getJsonResult(@PathVariable Map<String, String> pathVar){
		return Util.getJsonObject(pathVar).toString();
	}
	
	/**
	 * 
	 * @param pathVar
	 * @return
	 */
	@RequestMapping(value="/exception",produces="application/json")
	public String testException(){
		throw new NullPointerException("Error, I've made it.");
	}
	
}
