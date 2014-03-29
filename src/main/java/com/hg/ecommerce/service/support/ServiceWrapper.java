package com.hg.ecommerce.service.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class ServiceWrapper {
	
	private static final Logger logger = LogManager.getLogger("com.hg.ecommerce.service"); 
	
	/**
	 * wrap the method return boolean value
	 * @param point
	 * @return
	 */
	public boolean booleanAroundMethod(ProceedingJoinPoint point){
		try{
			logger.entry(point.getArgs());
			logger.info("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			return logger.exit((Boolean)point.proceed(point.getArgs()));
		}catch(Throwable exception){
			logger.catching(exception);
			return logger.exit(false);
		}
	}
	
	/**
	 * wrapper method return object, handle throwable.
	 * @param point
	 * @return
	 */
	public Object objectAroundMethod(ProceedingJoinPoint point){
		try{
			logger.entry(point.getArgs());
			logger.info("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			return logger.exit(point.proceed(point.getArgs()));
		}catch(Throwable exception){
			logger.catching(exception);
			return logger.exit(null);
		}
	}
}
