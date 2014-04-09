package com.hg.ecommerce.dao.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.dao.support.IOperators.AOR;
import com.hg.ecommerce.model.support.AnnotatedModel;
import com.hg.ecommerce.model.support.EntityObject;
import com.hg.ecommerce.util.Util;

/**
 * 此wrapper实现的是简单单表查询，不支持多表查询，连接查询，子查询
 * 注释"非调用"的函数，开发者无须调用
 * 不支持的查询请原生SQL实进行实现
 * @author JOE
 *
 */
public class SQLWrapper {
	
	private Object Model;
	
	private ISQLProvider provider = ProjectContainer.getInstance(ISQLProvider.class);
	
	private SQLWrapper(){}
	
	public static SQLWrapper instance(){
		return new SQLWrapper();
	}
	
	/**
	 * 降要执行insert into操作,后面跟fields().fields().values().values()
	 * @return
	 */
	public SQLWrapper insert(){
		provider.insert();
		return this;
	}
	
	/**
	 * 要插入一个实体，直接用insert(实体),不需要再调用其他value，字段名与字段值已经绑定
	 * @param Model:实体类对象
	 * @return
	 */
	public SQLWrapper insert(EntityObject Model){
		List<Object> fields = new ArrayList<Object>();
		List<Object> values = new ArrayList<Object>();
		AnnotatedModel meta = new AnnotatedModel(Model.getClass());
		if(Model!=null){
			Method[] methods = Model.getClass().getDeclaredMethods();
			String fieldName;
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
					  Object value = methods[i].invoke(Model, (Object[])null); // 值
					  fields.add(meta.getColumnName(fieldName));
					  if(value instanceof Boolean){
						  if((Boolean) value){
							  values.add(1);
						  }else{
							  values.add(0);
						  }
					  }else if(value instanceof Date) {
						  values.add(Util.dateToString((Date)value));
					  }else if(value == null){
						  values.add(ISQLProvider.NULL);
					  }else{
						  values.add(value);
					  }
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
			  	}
			}
		}
		provider.insert().fields(fields).values(values);
		return this;
	}
	
	/**
	 * 将要执行一个delete from，后面跟where()...
	 * @return
	 */
	public SQLWrapper delete(){
		provider.delete();
		return this;
	}
	
	/**
	 * 即将delete一个实体，该实体的主键值确认已经存在，后面不用跟其他操作
	 * @param Model
	 * @return
	 */
	public SQLWrapper delete(EntityObject Model){
		AnnotatedModel meta = new AnnotatedModel(Model.getClass());
		Set<String> set = meta.getPrimaryKeys();
		Method[] methods = Model.getClass().getDeclaredMethods();
		provider.delete().where();
		String fieldName;
		for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
					  fieldName = meta.getColumnName(fieldName);
					  if(set.contains(fieldName)){
						  provider.eq(fieldName,methods[i].invoke(Model, (Object[])null));
					  }
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
			  	}
			}
		return this;
	}
	
	/**
	 * 跟insert结合使用，为insert的字段一一赋值，请确保fields()跟values()的对应
	 * @param objects
	 * @return
	 */
	public SQLWrapper values(Object...objects){
		provider.values(objects);
		return this;
	}
	
	/**
	 * 跟insert结合使用，为insert的字段一一赋值，请确保fields()跟values()的对应
	 * @param objects
	 * @return
	 */
	public SQLWrapper values(Collection<Object> objects){
		provider.values(objects);
		return this;
	}
	
	/**
	 * 
	 * @param Model：要插入的实体类对象，直接使用insert(实体)的方法，
	 * @return：SQLWrapper
	 */
	public SQLWrapper values(EntityObject Model){
		List<Object> fields = new ArrayList<Object>();
		List<Object> values = new ArrayList<Object>();
		AnnotatedModel meta = new AnnotatedModel(Model.getClass());
		Method[] methods = Model.getClass().getDeclaredMethods();
		String fieldName;
		Object tempValue;
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
					  fieldName = meta.getColumnName(fieldName);
					  tempValue = methods[i].invoke(Model, (Object[])null);
					  if(null!=tempValue&&!tempValue.equals("")){
						  fields.add(fieldName);
						  values.add(tempValue);
					  }
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
			  	}
		}
		provider.fields(fields).values(values);
		return this;
	}
	
	/**
	 * 即将调用一个update set，后面跟set(field，value)
	 * @return
	 */
	public SQLWrapper update(){
		provider.update();
		return this;
	}
	
	/**
	 * Update一个实体
	 * @param Model
	 * @return
	 */
	public SQLWrapper update(EntityObject Model){
		List<String> fields = new ArrayList<String>();//字段
		List<Object> values = new ArrayList<Object>();//字段值
		List<String> keys = new ArrayList<String>();//主键
		List<Object> keyValues = new ArrayList<Object>();//主键值
		AnnotatedModel meta = new AnnotatedModel(Model.getClass());
		Set<String> set = meta.getPrimaryKeys();//获取主键
		
		if(Model!=null){
			Method[] methods = Model.getClass().getDeclaredMethods();
			String fieldName;
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
					  Object value = methods[i].invoke(Model, (Object[])null);  // 值
					  //String lsSourceType = methods[i].getReturnType().getName(); //类型
					  fieldName = meta.getColumnName(fieldName);
					  //判断是否是主键
					  if(set.contains(fieldName)){
						  keys.add(fieldName);
						  keyValues.add(value);
					  }else{
						  fields.add(fieldName);
						  if(value instanceof Boolean){
							  if((Boolean) value){
								  values.add(1);
							  }else{
								  values.add(0);
							  }
						  }else if(value instanceof Date){
							  values.add(Util.dateToString((Date)value));
						  }else {
							  values.add(value);
						  }
					  }
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
			  	}
			}
		}
		//set fields and values
		provider.update();
		for (int i=0,size=fields.size(); i<size; i++) {
			provider.set(fields.get(i), values.get(i));
		}
		provider.where();//where
		//set key and keyValue
		for(int i=0,size=keys.size(); i<size; i++){
			provider.eq(keys.get(i), keyValues.get(i));
		}
		return this;
	}
	
	/**
	 * 当不确保实体主键是否存在，可以使用upsert()，保存一个实体，若不存在，则新增一条记录
	 * @param Model
	 * @return
	 */
	public SQLWrapper upsert(EntityObject Model){
		AnnotatedModel meta = new AnnotatedModel(Model.getClass());
		Set<String> set = meta.getPrimaryKeys();//获取主键
		List<Object> values = new ArrayList<Object>();
		String fieldName;
		if(Model!=null){
			Method[] methods = Model.getClass().getDeclaredMethods();
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
					  Object value = methods[i].invoke(Model, (Object[])null);  // 值
					  fieldName = meta.getColumnName(fieldName);
					  //判断是否是主键
					  if(set.contains(fieldName) && null!=value){
						  values.add(value);
					  }
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				  
			  }
			}
		}
		if(values.size()<1){
			this.insert(Model);//add
		}else{
			this.update(Model);//update
		}
		return this;
	}
	
	/**
	 * 即将使用一个select from，后面跟fields()来选择要查的字段，where()条件
	 * @return
	 */
	public SQLWrapper select(){
		provider.select();
		return this;
	}
	
	/**
	 * 此方法返回一个select * from table
	 * @return
	 */	public SQLWrapper selectAll(){
		provider.selectAll();
		return this;
	}
	
	/**
	 * 聚合查询，AVG，COUNT等，projected提供基本常用的聚合函数
	 * @param projectedWrapper
	 * @return
	 */
	public SQLWrapper selectByProjectedWrapper(ProjectedWrapper projectedWrapper){
		provider.select(projectedWrapper.getProjecter());
		return this;
	}
	 
	/**
	 * 此方法只用于insert()之后，来确定要插入数据库的字段
	 * @param objects
	 * @return
	 */
	public SQLWrapper fields(Object...objects){
		provider.fields(objects);
		return this;
	}
	
	/**
	 * 此方法只用于insert()之后，来确定要插入数据库的字段
	 * @param objects
	 * @return
	 */
	public SQLWrapper fields(Collection<Object> objects){
		provider.fields(objects);
		return this;
	}
	
	/**
	 * 使用Model中的所有字段来填充fields，用于insert后
	 * @param Model
	 * @return
	 */
	public SQLWrapper fields(Class<EntityObject> Model){
		List<Object> fields = new ArrayList<Object>();//字段
		AnnotatedModel meta = new AnnotatedModel(Model);
		String fieldName;
		if(Model!=null){
			Method[] methods = Model.getClass().getDeclaredMethods();
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  fieldName = methods[i].getName().substring(3);   // 属性
				  fieldName = fieldName.toLowerCase().substring(0, 1)+fieldName.substring(1);
				  fields.add(meta.getColumnName(fieldName));
			  }
			}
		}
		return this;
	}
	
	/**
	 * 此方法用于update后，来update对应的字段和值
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper set(String field,Object value){
		provider.set(field, value);
		return this;
	}
	
	/**
	 * 此方法用于update后，来对数值字段进行加法
	 * @param field
	 * @param value：要相加的值
	 * @return
	 */
	public SQLWrapper inc(String field,long value){
		provider.inc(field, value);
		return this;
	}
	
	/**
	 * 此方法用于update后，来对数值字段进行剑法
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper dec(String field,long value){
		provider.dec(field, value);
		return this;
	}
	/**
	 * where条件，后面可跟eq()，ne()等等
	 * @return
	 */
	public SQLWrapper where(){
		provider.where();
		return this;
	}
	
	/**
	 * where后的and操作，这里的and操作是要SQLWrapper参数，把整个SQLWrapper子句都用and(SQLWrapper)包含
	 * @param wrapper：and() 括号中的子句
	 * @return
	 */
	public SQLWrapper and(SQLWrapper wrapper){
		provider.and(wrapper.getProvider());
		return this;
	}
	
	/**
	 * where后的and操作，这里的and操作是要两个SQLWrapper参数
	 * 生成and(SQLWrapper1，and/or，SQLWrapper2)包含
	 * @param one：子条件1
	 * @param another：子条件2
	 * @param AOR：and(one,and/or,two) 确定括号中间是用and还是or 来链接连个子句
	 * @return
	 */
	public SQLWrapper and(SQLWrapper one,SQLWrapper another,AOR AOR){
		provider.and(one.getProvider(),another.getProvider(),AOR);
		return this;
	}
	
	/**
	 * where后的or操作，这里的or操作是要SQLWrapper参数，把整个SQLWrapper子句都用or(SQLWrapper)包含
	 * @param wrapper
	 * @return
	 */
	public SQLWrapper or(SQLWrapper wrapper){
		provider.and(wrapper.getProvider());
		return this;
	}
	
	/**
	 * where后的or操作，这里的and操作是要两个SQLWrapper参数
	 * 生成or(SQLWrapper1，and/or，SQLWrapper2)
	 * @param one
	 * @param another
	 * @param AOR
	 * @return
	 */
	public SQLWrapper or(SQLWrapper one,SQLWrapper another,AOR AOR){
		provider.or(one.getProvider(), another.getProvider(), AOR);
		return this;
	}
	
	/**
	 * equals "="
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper eq(Object field,Object value){
		provider.eq(field, value);
		return this;
	}
	
	/**
	 * not equals "<>"
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper ne(Object field,Object value){
		provider.ne(field, value);
		return this;
	}
	
	/**
	 * great than ">"
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper gt(Object field,Object value){
		provider.gt(field, value);
		return this;
	}
	
	/**
	 * great than/equals ">="
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper ge(Object field,Object value){
		provider.ge(field, value);
		return this;
	}
	
	/**
	 * less than "<"
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper lt(Object field,Object value){
		provider.lt(field, value);
		return this;
	}
	
	/**
	 * less than/equals "<="
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper le(Object field,Object value){
		provider.le(field, value);
		return this;
	}
	
	/**
	 * "NOT"
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLWrapper not(Object field,Object value){
		provider.not(field, value);
		return this;
	}
	
	/**
	 * "between one and two"
	 * @param field
	 * @param lvalue
	 * @param rvalue
	 * @return
	 */
	public SQLWrapper between(Object field,Object lvalue,Object rvalue){
		provider.between(field, lvalue, rvalue);
		return this;
	}
	
	/**
	 * "in(a1,a2,a3...)"
	 * @param field
	 * @param objects
	 * @return
	 */
	public SQLWrapper in(Object field,Object...objects){
		provider.in(field, objects);
		return this;
	}
	
	/**
	 * "in(a1,a2,a3...)"
	 * @param field
	 * @param objects
	 * @return
	 */
	public SQLWrapper in(Object field,Collection<Object> objects){
		provider.in(field, objects);
		return this;
	}
	
	/**
	 * "not in(a1,a2,a3...)"
	 * @param field
	 * @param objects
	 * @return
	 */
	public SQLWrapper notIn(Object field,Object...objects){
		provider.notIn(field, objects);
		return this;
	}
	
	/**
	 * "not in(a1,a2,a3...)"
	 * @param field
	 * @param objects
	 * @return
	 */
	public SQLWrapper notIn(Object field,Collection<Object> objects){
		provider.notIn(field, objects);
		return this;
	}
	
	/**
	 * "like 'value'"
	 * @param field
	 * @param regex
	 * @return
	 */
	public SQLWrapper like(Object field,Object regex){
		provider.like(field, regex);
		return this;
	}
	
	/**
	 * "not like 'value'"
	 * @param field
	 * @param regex
	 * @return
	 */
	public SQLWrapper notLike(Object field,Object regex){
		provider.notLike(field, regex);
		return this;
	}
	
	/**
	 * "field is null"
	 * @param field
	 * @return
	 */
	public SQLWrapper isNull(Object field){
		provider.isNull(field);
		return this;
	}
	
	/**
	 * "field is not null"
	 * @param field
	 * @return
	 */
	public SQLWrapper isNotNull(Object field){
		provider.isNotNull(field);
		return this;
	}
	
	/**
	 * ORDER BY 子句，排序
	 * @param sortable：filed.ASC/field.DESC
	 * @return
	 */
	public SQLWrapper orderBy(Sortable sortable){
		provider.orderBy(sortable.getField(), sortable.getSort());
		return this;
	}
	/**
	 * limit 控制分页
	 * @param pageable
	 * @return
	 */
	public SQLWrapper limit(Pageable pageable){
		provider.limit(pageable.getOffset(), pageable.getPageSize());
		return this;
	}
	
	/**
	 * 非调用
	 * @return
	 */
	public ISQLProvider getProvider() {
		return provider;
	}
	
	/**
	 * 非调用
	 * @param provider
	 */
	public void setProvider(ISQLProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * 非调用
	 * @return
	 */
	public String getQuery() {
		return this.provider.getSQL();
	}
	/**
	 * 非调用
	 * @return
	 */
	public Object getModel() {
		return Model;
	}
	/**
	 * 非调用
	 * @param model
	 * @return
	 */
	public SQLWrapper setModel(Object model) {
		this.Model = model;
		provider.setModel(model);
		return this;
	}
	/**
	 * 非调用
	 * @param cls
	 * @return
	 */
	public String preparedInsert(Class<EntityObject> cls){
		Field[] fields = cls.getDeclaredFields();
		AnnotatedModel meta = new AnnotatedModel(cls);
		String query = getQuery();
		String[] parts = query.split(ISQLProvider.VALUES);
		if(parts.length>1){
			String fieldsPart = parts[0].substring(parts[0].indexOf(ISQLProvider.LP)+ISQLProvider.LP.length(),parts[0].indexOf(ISQLProvider.RP)+1);
			String[] currentFields = fieldsPart.trim().split(ISQLProvider.COMMA);
			List<String> currFieldsList = Arrays.asList(currentFields);
			StringBuilder newFields = new StringBuilder(parts[0]);//origin fields
			StringBuilder newValues = new StringBuilder(parts[1]);//origin values
			
			for(Field field : fields){
				if(null!=meta.getColumnName(field.getName())&&!meta.getColumnName(field.getName()).equals("")){
					if(!currFieldsList.contains(meta.getColumnName(field.getName()))){
						newFields.insert(newFields.indexOf(ISQLProvider.RP), ISQLProvider.COMMA+meta.getColumnName(field.getName()));
						newValues.insert(newValues.indexOf(ISQLProvider.RP), ISQLProvider.COMMA+Types.NULL);
					}
				}
			}
			query = newFields+ISQLProvider.VALUES+newValues;
		}
		return query;
	}
}
