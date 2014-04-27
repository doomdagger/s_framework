package com.hg.ecommerce.test;

import java.util.HashMap;
import java.util.Map;

public class MapKeyMainTest {
	public static void main(String[] args){
		
		Map<String,String> map = new HashMap<String,String>();
		
		
		map.put("1234", "haha");
		
		map.remove("12345");
		
		System.out.println(map.size());
		
	}
}
