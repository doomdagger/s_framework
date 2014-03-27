package com.hg.ecommerce.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hg.ecommerce.config.ProjectConfig;

/**
 * Utilizer class: contain several helper methods
 * @author apple
 *
 */
public class Util {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(ProjectConfig.getProperty("format.date"));
	
	/**
	 * 将java.util.Date对象转换为string，格式规定为project.properties文件中
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(date==null){
			date = new Date();
		}
		return dateFormat.format(date);
	}
	
	/**
	 * 将string转换为java.util.Date，格式规定为project.properties文件中
	 * @param string
	 * @return
	 */
	public static Date stringToDate(String string){
		try {
			return dateFormat.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	
	public static final String SETTER_PREFIX = "set";
	public static final String GETTER_PREFIX = "get";
	public static final String IS_PREFIX = "is";

	/**
	 * 此方法用于快速映射两个对象的相同字段，方法会创建一个与pojo对象对应的dto对象，
	 * 用于从pojo对象中复制字段值到dto对象，这些字段必须是pojo对象与dto对象都共同存在的。
	 * 
	 * 需要借用project.properties指定的dto所在的package
	 * 使用此转换规则需要遵循一定的条件：
	 * 	1.类命名条件：pojo类命名为：Area，对应dto类命名为：AreaDto
	 * 	2.字段命名规则：想要快速映射的字段，在Area与AreaDto中应该具有一样的名字和getter与setter方法
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object pojoToDto(Object pojo){
		try{
			Class pojoCls = pojo.getClass();
			Class dtoCls = Class.forName(ProjectConfig.getProperty("package.dto")+"."+pojoCls.getSimpleName()+"Dto");
			
			Method[] pojoMethods = pojoCls.getDeclaredMethods();
			Method[] dtoMethods = dtoCls.getDeclaredMethods();
			
			Object dto = dtoCls.newInstance();
			
			for(Method dtoMethod : dtoMethods){
				String methodName = dtoMethod.getName();
				if(!methodName.startsWith(SETTER_PREFIX)){
					continue;
				}
				Method pojoMethod = endsSameMethod(methodName, pojoMethods);
				if(pojoMethod==null){
					continue;
				}
				dtoMethod.invoke(dto, pojoMethod.invoke(pojo, (Object[])null));
			}
			
			return dto;
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 此方法用于快速映射两个对象的相同字段，方法会创建一个与dto对象对应的pojo对象，
	 * 用于从dto对象中复制字段值到pojo对象，这些字段必须是pojo对象与dto对象都共同存在的。
	 * 需要借用project.properties指定的dto所在的package
	 * 使用此转换规则需要遵循一定的条件：
	 * 	1.类命名条件：pojo类命名为：Area，对应dto类命名为：AreaDto
	 * 	2.字段命名规则：想要快速映射的字段，在Area与AreaDto中应该具有一样的名字和getter与setter方法
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object dtoToPojo(Object dto) {
		try{
			Class dtoCls = dto.getClass();
			Class pojoCls = Class.forName(ProjectConfig.getProperty("package.pojo")+"."+dtoCls.getSimpleName().substring(0,dtoCls.getSimpleName().indexOf("Dto")));
			
			Method[] pojoMethods = pojoCls.getDeclaredMethods();
			Method[] dtoMethods = dtoCls.getDeclaredMethods();
			
			Object pojo = pojoCls.newInstance();
			
			for(Method pojoMethod : pojoMethods){
				String methodName = pojoMethod.getName();
				if(!methodName.startsWith(SETTER_PREFIX)){
					continue;
				}
				Method dtoMethod = endsSameMethod(methodName, dtoMethods);
				if(dtoMethod==null){
					continue;
				}
				pojoMethod.invoke(pojo, dtoMethod.invoke(dto, (Object[])null));
			}
			
			return pojo;
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
	}
	/**
	 * 查看两个方法是否一个为setter，一个getter，并且是针对同样名称的字段操作
	 * @param method1
	 * @param method2
	 * @return
	 */
	private static boolean endsSame(String method1, String method2){
		String affix1 = null;
		String affix2 = null;
		if(method1.startsWith(SETTER_PREFIX)&&!method2.startsWith(SETTER_PREFIX)){
			affix1 = method1.substring(SETTER_PREFIX.length());
			if(method2.startsWith(GETTER_PREFIX)){
				affix2 = method2.substring(GETTER_PREFIX.length());
			}else if(method2.startsWith(IS_PREFIX)){
				affix2 = method2.substring(IS_PREFIX.length());
			}			
		}else if(method2.startsWith(SETTER_PREFIX)&&!method1.startsWith(SETTER_PREFIX)){
			affix2 = method2.substring(SETTER_PREFIX.length());
			if(method1.startsWith(GETTER_PREFIX)){
				affix1 = method1.substring(GETTER_PREFIX.length());
			}else if(method1.startsWith(IS_PREFIX)){
				affix1 = method1.substring(IS_PREFIX.length());
			}
		}
		
		if(affix1==null||affix2==null){
			return false;
		}else{
			return affix1.equals(affix2);
		}
	}
	
	/**
	 * 根据endsSame提供的boolean返回值，获取合适的method对象
	 * @param method1
	 * @param methods
	 * @return
	 */
	private static Method endsSameMethod(String method1, Method[] methods){
		for(Method method: methods){
			if(endsSame(method1, method.getName())){
				return method;
			}else{
				continue;
			}
		}
		return null;
	}
}