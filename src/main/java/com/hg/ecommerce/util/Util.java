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
	 * ��java.util.Date����ת��Ϊstring����ʽ�涨Ϊproject.properties�ļ���
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
	 * ��stringת��Ϊjava.util.Date����ʽ�涨Ϊproject.properties�ļ���
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
	 * �˷������ڿ���ӳ�������������ͬ�ֶΣ������ᴴ��һ����pojo�����Ӧ��dto����
	 * ���ڴ�pojo�����и����ֶ�ֵ��dto������Щ�ֶα�����pojo������dto���󶼹�ͬ���ڵġ�
	 * 
	 * ��Ҫ����project.propertiesָ����dto���ڵ�package
	 * ʹ�ô�ת��������Ҫ��ѭһ����������
	 * 	1.������������pojo������Ϊ��Area����Ӧdto������Ϊ��AreaDto
	 * 	2.�ֶ�����������Ҫ����ӳ����ֶΣ���Area��AreaDto��Ӧ�þ���һ�������ֺ�getter��setter����
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
	 * �˷������ڿ���ӳ�������������ͬ�ֶΣ������ᴴ��һ����dto�����Ӧ��pojo����
	 * ���ڴ�dto�����и����ֶ�ֵ��pojo������Щ�ֶα�����pojo������dto���󶼹�ͬ���ڵġ�
	 * ��Ҫ����project.propertiesָ����dto���ڵ�package
	 * ʹ�ô�ת��������Ҫ��ѭһ����������
	 * 	1.������������pojo������Ϊ��Area����Ӧdto������Ϊ��AreaDto
	 * 	2.�ֶ�����������Ҫ����ӳ����ֶΣ���Area��AreaDto��Ӧ�þ���һ�������ֺ�getter��setter����
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
	 * �鿴���������Ƿ�һ��Ϊsetter��һ��getter�����������ͬ�����Ƶ��ֶβ���
	 * @param method1
	 * @param method2
	 * @return
	 */
	public static boolean endsSame(String method1, String method2){
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
	 * ����endsSame�ṩ��boolean����ֵ����ȡ���ʵ�method����
	 * @param method1
	 * @param methods
	 * @return
	 */
	public static Method endsSameMethod(String method1, Method[] methods){
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