package com.hg.ecommerce.dao.support;

import java.util.UUID;

/**
 * 生成UUID
 * @author Li He
 *
 */
public class UUIDGenerator {
	public static String randomUUID(){
		return UUID.randomUUID().toString();
	}
}
