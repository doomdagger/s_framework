package com.hg.ecommerce.service.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component("serviceWrapper")
public class ServiceWrapper {
	/**
	 * 如果返回类型为boolean，自动包装这类方法
	 * @param point
	 * @return
	 */
	public boolean booleanAroundMethod(ProceedingJoinPoint point){
		try{
			//logger.entry(point.getArgs());
			//logger.info("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			//return logger.exit((Boolean)point.proceed(point.getArgs()));
			return (Boolean)point.proceed(point.getArgs());
		}catch(Throwable exception){
			//logger.catching(exception);
			exception.printStackTrace();
			//return logger.exit(false);
			return false;
		}
	}
	
	/**
	 * wrapper method return object, handle throwable.
	 * @param point
	 * @return
	 */
	public Object objectAroundMethod(ProceedingJoinPoint point){
		try{
			//logger.entry(point.getArgs());
			//logger.info("In "+point.getTarget().getClass().getName()+" "+point.getSignature().getName());
			//return logger.exit(point.proceed(point.getArgs()));
			return point.proceed(point.getArgs());
		}catch(Throwable exception){
			//logger.catching(exception);
			exception.printStackTrace();
			//return logger.exit(null);
			return null;
		}
	}
}
