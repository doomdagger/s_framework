package com.hg.ecommerce.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hg.ecommerce.config.ProjectConfig;

/**
 * This listener must placed before log4j listener.
 * <strong>Only This</strong> can we modify the log path before the log4j
 * has been initialized.
 * 
 * @author Li He
 *
 */
public class ProjectBootstrapListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//ensuring log path in System Property
		System.setProperty("log.path", ProjectConfig.getProperty("log.path"));
		
	}

}
