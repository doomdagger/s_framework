package com.hg.ecommerce.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hg.ecommerce.config.ProjectConfig;
import com.hg.ecommerce.model.support.EntityObject;

/**
 * Utilizer class: contain several helper methods
 * 
 * @author apple
 * 
 */
public class Util {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			ProjectConfig.getProperty("format.date"));

	/**
	 * 将java.util.Date对象转换为string，格式规定为project.properties文件中
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			date = new Date();
		}
		return dateFormat.format(date);
	}

	/**
	 * 将string转换为java.util.Date，格式规定为project.properties文件中
	 * 
	 * @param string
	 * @return
	 */
	public static Date stringToDate(String string) {
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
	 * 需要借用project.properties指定的dto所在的package 使用此转换规则需要遵循一定的条件：
	 * 1.类命名条件：pojo类命名为：Area，对应dto类命名为：AreaDto
	 * 2.字段命名规则：想要快速映射的字段，在Area与AreaDto中应该具有一样的名字和getter与setter方法
	 * 
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object pojoToDto(Object pojo) {
		try {
			Class pojoCls = pojo.getClass();
			Class dtoCls = Class.forName(ProjectConfig
					.getProperty("package.dto")
					+ "."
					+ pojoCls.getSimpleName()
					+ "Dto");

			Method[] pojoMethods = pojoCls.getDeclaredMethods();
			Method[] dtoMethods = dtoCls.getDeclaredMethods();

			Object dto = dtoCls.newInstance();

			for (Method dtoMethod : dtoMethods) {
				String methodName = dtoMethod.getName();
				if (!methodName.startsWith(SETTER_PREFIX)) {
					continue;
				}
				Method pojoMethod = endsSameMethod(methodName, pojoMethods);
				if (pojoMethod == null) {
					continue;
				}
				dtoMethod.invoke(dto, pojoMethod.invoke(pojo, (Object[]) null));
			}

			return dto;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * 此方法用于快速映射两个对象的相同字段，方法会创建一个与dto对象对应的pojo对象，
	 * 用于从dto对象中复制字段值到pojo对象，这些字段必须是pojo对象与dto对象都共同存在的。
	 * 需要借用project.properties指定的dto所在的package 使用此转换规则需要遵循一定的条件：
	 * 1.类命名条件：pojo类命名为：Area，对应dto类命名为：AreaDto
	 * 2.字段命名规则：想要快速映射的字段，在Area与AreaDto中应该具有一样的名字和getter与setter方法
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object dtoToPojo(Object dto) {
		try {
			Class dtoCls = dto.getClass();
			Class pojoCls = Class.forName(ProjectConfig
					.getProperty("package.pojo")
					+ "."
					+ dtoCls.getSimpleName().substring(0,
							dtoCls.getSimpleName().indexOf("Dto")));

			Method[] pojoMethods = pojoCls.getDeclaredMethods();
			Method[] dtoMethods = dtoCls.getDeclaredMethods();

			Object pojo = pojoCls.newInstance();

			for (Method pojoMethod : pojoMethods) {
				String methodName = pojoMethod.getName();
				if (!methodName.startsWith(SETTER_PREFIX)) {
					continue;
				}
				Method dtoMethod = endsSameMethod(methodName, dtoMethods);
				if (dtoMethod == null) {
					continue;
				}
				pojoMethod.invoke(pojo, dtoMethod.invoke(dto, (Object[]) null));
			}

			return pojo;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * 查看两个方法是否一个为setter，一个getter，并且是针对同样名称的字段操作
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	private static boolean endsSame(String method1, String method2) {
		String affix1 = null;
		String affix2 = null;
		if (method1.startsWith(SETTER_PREFIX)
				&& !method2.startsWith(SETTER_PREFIX)) {
			affix1 = method1.substring(SETTER_PREFIX.length());
			if (method2.startsWith(GETTER_PREFIX)) {
				affix2 = method2.substring(GETTER_PREFIX.length());
			} else if (method2.startsWith(IS_PREFIX)) {
				affix2 = method2.substring(IS_PREFIX.length());
			}
		} else if (method2.startsWith(SETTER_PREFIX)
				&& !method1.startsWith(SETTER_PREFIX)) {
			affix2 = method2.substring(SETTER_PREFIX.length());
			if (method1.startsWith(GETTER_PREFIX)) {
				affix1 = method1.substring(GETTER_PREFIX.length());
			} else if (method1.startsWith(IS_PREFIX)) {
				affix1 = method1.substring(IS_PREFIX.length());
			}
		}

		if (affix1 == null || affix2 == null) {
			return false;
		} else {
			return affix1.equals(affix2);
		}
	}

	/**
	 * 根据endsSame提供的boolean返回值，获取合适的method对象
	 * 
	 * @param method1
	 * @param methods
	 * @return
	 */
	private static Method endsSameMethod(String method1, Method[] methods) {
		for (Method method : methods) {
			if (endsSame(method1, method.getName())) {
				return method;
			} else {
				continue;
			}
		}
		return null;
	}

	/**
	 * 转换为JSON化对象，可序列化。
	 * 
	 * @param object
	 *            要转化的对象
	 * @return 返回JSONObject
	 */
	@SuppressWarnings("rawtypes")
	public static Object getJsonObject(Object object) {

		if (object == null) {
			return null;
		}

		if (object instanceof EntityObject) { // 字段对象为EntityObject
			object = ((EntityObject) object).toJSON();
		} else if (object instanceof Date) { // 字段对象为Date
			object = ((Date) object).getTime();
		} else if (object instanceof Map) {
			JSONObject jsonObject = new JSONObject();
			for (Object obj : ((Map) object).entrySet()) {
				Entry entry = (Entry) obj;
				// 如果为map，必须保证key为String
				if (!(entry.getKey() instanceof String)) {
					throw new RuntimeException(
							"java.util.Map has Non-String Key, not allowed!");
				}
				jsonObject.put(entry.getKey().toString(),
						getJsonObject(entry.getValue()));
			}

			object = jsonObject;

		} else if (object instanceof Collection) {
			JSONArray jsonArray = new JSONArray();

			for (Object obj : (Collection) object) {
				jsonArray.put(getJsonObject(obj));
			}

			object = jsonArray;
		}

		return object;
	}

	/**
	 * 以某种token连接字符串数组
	 * 
	 * @param token
	 * @param parts
	 * @return
	 */
	public static String join(char token, String... parts) {
		StringBuilder sb = new StringBuilder();
		if (parts.length < 1)
			return "";

		sb.append(parts[0]);

		for (int i = 1; i < parts.length; ++i) {
			sb.append(token);
			sb.append(parts[i]);
		}
		return sb.toString();
	}

	/**
	 * 类似于模板引擎，将render mustache模板文件
	 * 
	 * @param reader
	 * @param writer
	 * @param object
	 * @throws IOException
	 */
	public static void render(List<String> mustache, PrintWriter writer,
			JSONObject jsonObject) {

		String line;

		Pattern pattern = Pattern.compile(
				"\\{\\{([#/]?)([a-zA-Z0-9\\-_$\\.]+)\\}\\}", Pattern.MULTILINE);

		ListIterator<String> iterator = mustache.listIterator();
		while (iterator.hasNext()) {
			boolean shouldPrint = true;
			String mstr = iterator.next();

			Matcher matcher = pattern.matcher(mstr);

			int sequenceIndex = 0;
			boolean shouldContinue = true;
			while (matcher.find(sequenceIndex)) {
				// 更新下标，避免死循环
				sequenceIndex = matcher.end(0);
				if (!shouldContinue)
					break;

				// System.err.println(matcher.group(0));
				// System.err.println(matcher.group(1));
				// System.err.println(matcher.group(2));
				// System.err.println();

				String prefix = matcher.group(1);
				String token = matcher.group(2);
				if (prefix == null || prefix.trim().equals("")) {
					Object replacement = "";
					try {
						replacement = jsonObject.get(token);
					} catch (Exception exception) {
						System.err.println("Has no property named \"" + token
								+ "\"");
					}
					// 直接覆盖mstr应当不影响Matcher对象
					mstr = mstr.replace("{{" + token + "}}",
							replacement.toString());
				} else {
					mstr = mstr.replace("{{" + prefix + token + "}}", "");

					// 递归判定开始
					if ("#".equals(prefix)) {

						Object subObject = null;
						try {
							subObject = jsonObject.get(token);
						} catch (Exception exception) {
							System.err.println("Has no property named \""
									+ token + "\"");
							// continue;
						}

						// 判定递归成功，不应再让Matcher查找下去
						shouldContinue = false;

						// 我的策略是如果用户在模板中指定的集合字段或boolean字段不存在，标签体中的一切都要放弃掉
						if (subObject != null) {
							//创建list，收集标签体中的语句
							List<String> subList = new ArrayList<String>();

							subList.add(mstr);
							while (iterator.hasNext()) {
								line = iterator.next();
								// 添加行数，知道遇到结束标签
								if (line.contains("{{/" + token + "}}")) {
									iterator.previous();
									break;
								} else {
									subList.add(line);
								}
							}
							//收集完成后，判断对象类型，决定处理手段，如果是array，遍历array，多次递归调用render，如果是boolean。。。见下面的else
							if (subObject instanceof JSONArray) {
								// 转型为 JSONArray
								JSONArray array = (JSONArray) subObject;
								for (int i = 0; i < array.length(); ++i) {
									// JSONArray中的不一定是JSONObject 需要分情况讨论
									Object inner = array.get(i);
									JSONObject innerJsonObject = null;
									if (inner instanceof JSONObject) {
										innerJsonObject = (JSONObject) inner;
									} else if (inner instanceof JSONArray) {

									} else {
										innerJsonObject = new JSONObject();
										innerJsonObject.put(".", inner);
									}

									render(subList, writer, innerJsonObject);
								}
							} else {
								boolean dealFlag = true;
								// 除了JSONArray之外，其他相似标签一律作true or false判定
								if(subObject.getClass().getName().equals("boolean")||subObject instanceof Boolean){
									if(!(Boolean)subObject){
										//设置不作处理
										dealFlag = false;
									}
								}
								//判断是否处理
								if(dealFlag){
									render(subList, writer, jsonObject);
								}
							}
						} else {
							// 空找结束标签，抛弃体中的一切东西
							while (iterator.hasNext()) {
								line = iterator.next();
								// 添加行数，知道遇到结束标签
								if (line.contains("{{/" + token + "}}")) {
									iterator.previous();
									break;
								}
							}
						}

					} else {
						shouldPrint = false;
						continue;
					}
				}

			}
			// System.err.println(mstr);
			if(shouldPrint&&!mstr.trim().equals(""))
				writer.println(mstr);
		}

	}

	/**
	 * 格式化字段名称
	 * 
	 * @param mappedFieldName
	 * @return
	 */
	public static String qualifyFieldName(String mappedFieldName) {
		StringBuilder sb = new StringBuilder();

		String[] parts = mappedFieldName.split("_");

		sb.append(parts[0]);
		for (int i = 1; i < parts.length; i++) {
			sb.append(repairName(parts[i]));
		}

		return sb.toString();
	}

	/**
	 * 格式化实体名称
	 * 
	 * @param tableName
	 * @return
	 */
	public static String qualifyModelName(String tableName) {
		StringBuilder sb = new StringBuilder();

		String[] parts = tableName.split("_");

		for (int i = 0; i < parts.length; i++) {
			sb.append(repairName(parts[i]));
		}

		return sb.toString();
	}

	/**
	 * 保证首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String repairName(String name) {
		if (name.length() < 1)
			return "";

		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	/**
	 * 如果入参为空，返回默认值
	 */
	public static <T> T conditionedGet(T in, T defaultVal) {
		if (in == null)
			return defaultVal;

		if (in instanceof CharSequence) {
			if (in.toString().equals(""))
				return defaultVal;
		}

		return in;
	}
}