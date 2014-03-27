package com.hg.ecommerce.config;

import java.io.IOException;
import java.util.Properties;

/**
 * for configuration. load properties from file -- project.properties
 * static one
 * @author Li He
 *
 */
public class ProjectConfig {
	private static String location = "/project.properties";
	private static Properties props;
	static {
		props = new Properties();
		try {
			props.load(ProjectConfig.class
					.getResourceAsStream(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回String类型的配置信息
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return (String) props.get(name);
	}

	public static boolean getBooleanProperty(String name) {
		return Boolean.valueOf((String) props.get(name));
	}

	public static int getIntegerProperty(String name) {
		return Integer.valueOf((String) props.get(name));
	}
	
	public static long getLongProperty(String name){
		return Long.valueOf((String) props.get(name));
	}

	public static double getDoubleProperty(String name) {
		return Double.valueOf((String) props.get(name));
	}

	public static void reloadProps() {
		props = new Properties();
		try {
			props.load(ProjectConfig.class
					.getResourceAsStream(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}