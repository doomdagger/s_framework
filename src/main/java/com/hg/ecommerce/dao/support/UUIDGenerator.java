package com.hg.ecommerce.dao.support;

import java.util.UUID;

public class UUIDGenerator {
	public static String randomUUID(){
		return UUID.randomUUID().toString();
	}
}
