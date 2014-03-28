package com.hg.ecommerce.model.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hg.ecommerce.config.ProjectConfig;

public class EntityGenerator {
	private Connection connection;
	{
		String url = ProjectConfig.getProperty("db.url");
		String username = ProjectConfig.getProperty("db.username");
		String password = ProjectConfig.getProperty("db.password");
		try{
			connection = DriverManager.getConnection(url, username, password);
			
		}catch(SQLException exception){
			
		}
	}
}
