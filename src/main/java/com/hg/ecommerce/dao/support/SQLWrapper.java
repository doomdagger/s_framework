package com.hg.ecommerce.dao.support;

import java.util.Collection;

import com.hg.ecommerce.config.ProjectContainer;
import com.hg.ecommerce.dao.support.ISQLProvider.AOR;
import com.hg.ecommerce.dao.support.ISQLProvider.SORT;
import com.hg.ecommerce.model.support.EntityObject;

public class SQLWrapper {
	
	private String Model;
	
	private StringBuffer query;
	
	private ISQLProvider provider = ProjectContainer.getInstance(ISQLProvider.class);
	
	
	private SQLWrapper(){}
	
	public static SQLWrapper instance(){
		return new SQLWrapper();
	}
	
	// operations
	public SQLWrapper insert(){
		provider.insert();
		return this;
	}
	
	public SQLWrapper delete(){
		provider.delete();
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
	
	public SQLWrapper fields(EntityObject model){
		// TODO:GOGOGO
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
	
	public SQLWrapper orderBy(String field,SORT sort){
		provider.orderBy(field, sort);
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

	public void setModel(String model) {
		Model = model;
	}
}
