package com.hg.ecommerce.dao.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.dao.support.IOperators.AOR;
import com.hg.ecommerce.model.support.EntityObject;

public class SQLWrapper {
	
	private String Model;
	
	private StringBuilder query;
	
	private ISQLProvider provider = ProjectContainer.getInstance(ISQLProvider.class);
	
	
	private SQLWrapper(){
		this.query = new StringBuilder();
	}
	
	public static SQLWrapper instance(){
		return new SQLWrapper();
	}
	
	// operations
	public SQLWrapper insert(){
		provider.insert();
		return this;
	}
	
	public SQLWrapper insert(EntityObject Model){
		List<String> fields = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		if(Model!=null){
			Method[] methods = Model.getClass().getMethods();
			String fieldName;
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  Object value = methods[i].invoke(Model, new Object());  // 值
					  //String lsSourceType = methods[i].getReturnType().getName(); //类型
					  fields.add(Model.getColumnName(fieldName));
					  values.add(value);
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
	
	public SQLWrapper delete(){
		provider.delete();
		return this;
	}
	
	public SQLWrapper delete(EntityObject Model){
		return this;
	}
	
	public SQLWrapper values(Object...objects){
		provider.values(objects);
		return this;
	}
	
	public SQLWrapper values(Collection<Object> objects){
		provider.values(objects);
		return this;
	}
	
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
		String tempName;
		Set<String> set = ((EntityObject)Model).getPrimaryKeys();//获取主键
		
		if(Model!=null){
			Method[] methods = Model.getClass().getMethods();
			String fieldName;
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  Object value = methods[i].invoke(Model, new Object());  // 值
					  //String lsSourceType = methods[i].getReturnType().getName(); //类型
					  tempName = Model.getColumnName(fieldName);
					  //判断是否是主键
					  if(set.contains(tempName)){
						  keys.add(tempName);
						  keyValues.add(value);
					  }else{
						  fields.add(tempName);
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
	
	public SQLWrapper upsert(EntityObject Model){
		Set<String> set = ((EntityObject)Model).getPrimaryKeys();//获取主键
		List<Object> values = new ArrayList<Object>();
		String fieldName;
		String tempName;
		if(Model!=null){
			Method[] methods = Model.getClass().getMethods();
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  try {
					  fieldName = methods[i].getName().substring(3);   // 属性
					  Object value = methods[i].invoke(Model, new Object());  // 值
					  tempName = Model.getColumnName(fieldName);
					  //判断是否是主键
					  if(set.contains(tempName) && null!=value){
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
	
	public SQLWrapper select(){
		provider.select();
		return this;
	}
	
	public SQLWrapper selectAll(){
		provider.selectAll();
		return this;
	}
	
	public SQLWrapper fields(Object[] objects){
		provider.fields(objects);
		return this;
	}
	
	public SQLWrapper fields(Collection<Object> objects){
		provider.fields(objects);
		return this;
	}
	
	/**
	 * 使用Model中的所有字段来填充fields
	 * @param Model
	 * @return
	 */
	public SQLWrapper fields(EntityObject Model){
		List<String> fields = new ArrayList<String>();//字段
		String fieldName;
		if(Model!=null){
			Method[] methods = Model.getClass().getMethods();
			for(int i=0;i<methods.length;i++){
			  if(methods[i].getName().startsWith("get")){
				  fieldName = methods[i].getName().substring(3);   // 属性
				  fields.add(Model.getColumnName(fieldName));
			  }
			}
		}
		return this;
	}
	
	
	public SQLWrapper set(String field,Object value){
		provider.set(field, value);
		return this;
	}
	
	public SQLWrapper inc(String field,long value){
		provider.inc(field, value);
		return this;
	}
	
	public SQLWrapper dec(String field,long value){
		provider.dec(field, value);
		return this;
	}
	
	public SQLWrapper where(){
		provider.where();
		return this;
	}
	
	public SQLWrapper and(SQLWrapper wrapper){
		provider.and(wrapper.getProvider());
		return this;
	}
	
	public SQLWrapper and(SQLWrapper one,SQLWrapper another,AOR AOR){
		provider.and(one.getProvider(),another.getProvider(),AOR);
		return this;
	}
	
	public SQLWrapper or(SQLWrapper wrapper){
		provider.and(wrapper.getProvider());
		return this;
	}
	
	public SQLWrapper or(SQLWrapper one,SQLWrapper another,AOR AOR){
		provider.or(one.getProvider(), another.getProvider(), AOR);
		return this;
	}
	
	public SQLWrapper eq(String field,Object value){
		provider.eq(field, value);
		return this;
	}
	
	public SQLWrapper ne(String field,Object value){
		provider.ne(field, value);
		return this;
	}
	
	public SQLWrapper gt(String field,Object value){
		provider.gt(field, value);
		return this;
	}
	
	public SQLWrapper ge(String field,Object value){
		provider.ge(field, value);
		return this;
	}
	
	public SQLWrapper lt(String field,Object value){
		provider.lt(field, value);
		return this;
	}
	
	public SQLWrapper le(String field,Object value){
		provider.le(field, value);
		return this;
	}
	
	public SQLWrapper not(String field,Object value){
		provider.not(field, value);
		return this;
	}
	
	public SQLWrapper between(String field,Object lvalue,Object rvalue){
		provider.between(field, lvalue, rvalue);
		return this;
	}
	
	public SQLWrapper in(String field,Object...objects){
		provider.in(field, objects);
		return this;
	}
	
	public SQLWrapper in(String field,Collection<Object> objects){
		provider.in(field, objects);
		return this;
	}
	
	public SQLWrapper notIn(String field,Object...objects){
		provider.notIn(field, objects);
		return this;
	}
	
	public SQLWrapper notIn(String field,Collection<Object> objects){
		provider.notIn(field, objects);
		return this;
	}
	
	public SQLWrapper like(String field,Object regex){
		provider.like(field, regex);
		return this;
	}
	
	public SQLWrapper notLike(String field,Object regex){
		provider.notLike(field, regex);
		return this;
	}
	
	public SQLWrapper isNull(String field){
		provider.isNull(field);
		return this;
	}
	
	public SQLWrapper isNotNull(String field){
		provider.isNotNull(field);
		return this;
	}
	
	public SQLWrapper orderBy(Sortable sortable){
		provider.orderBy(sortable.getField(), sortable.getSort());
		return this;
	}
	
	public SQLWrapper limit(Pageable pageable){
		provider.limit(pageable.getOffset(), pageable.getPageSize());
		return this;
	}

	public ISQLProvider getProvider() {
		return provider;
	}

	public void setProvider(ISQLProvider provider) {
		this.provider = provider;
	}

	//only get, no set
	public String getQuery() {
		return this.query.toString();
	}

	public String getModel() {
		return Model;
	}

	public SQLWrapper setModel(String model) {
		this.Model = model;
		provider.setModel(model);
		return this;
	}
}
