package com.hg.ecommerce.action;

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
@RequestMapping("/webservice")
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
}
