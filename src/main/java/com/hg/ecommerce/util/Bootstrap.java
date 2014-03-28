package com.hg.ecommerce.util;

import java.io.IOException;

import com.hg.ecommerce.model.support.EntityGenerator;

/**
 * use this class to bootstrap the project.
 * There is no need for developers to write model classes themselves.
 * Once there are some updates or modified parts in the database, 
 * Please run this class as JavaApplication to update Modal class files.
 * 
 * @author lihe
 *
 */
public class Bootstrap {
	public static void main(String[] args){
		EntityGenerator generator = new EntityGenerator();
		try {
			generator.generateModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
