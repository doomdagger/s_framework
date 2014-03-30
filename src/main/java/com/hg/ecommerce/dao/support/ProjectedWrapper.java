package com.hg.ecommerce.dao.support;

import java.util.Collection;

import com.hg.ecommerce.config.ProjectContainer;

public class ProjectedWrapper {
	
	private StringBuilder projection;
	
	private IProjections projecter = ProjectContainer.getInstance(IProjections.class);
	
	private ProjectedWrapper(){
		this.projection = new StringBuilder();
	}
	
	public static ProjectedWrapper instance(){
		return new ProjectedWrapper();
	}
	
	//fields
	public ProjectedWrapper fields(Object...objects){
		projecter.fields(objects);
		return this;
	}
	
	public ProjectedWrapper fields(Collection<Object> objects){
		projecter.fields(objects);
		return this;
	}
	
	//projections  functions; usually be used for select
	public ProjectedWrapper AVG(String field,String alias){
		projecter.AVG(field, alias);
		return this;
	}
	
	public ProjectedWrapper COUNT(String alias){
		projecter.COUNT(alias);
		return this;
	}
	
	public ProjectedWrapper COUNT(String field,String alias){
		projecter.COUNT(field, alias);
		return this;
	}
	
	public ProjectedWrapper FIRST(String field,String alias){
		projecter.FIRST(field, alias);
		return this;
	}
	
	public ProjectedWrapper LAST(String field,String alias){
		projecter.LAST(field, alias);
		return this;
	}
	
	public ProjectedWrapper MAX(String field,String alias){
		projecter.MAX(field, alias);
		return this;
	}
	
	public ProjectedWrapper MIN(String field,String alias){
		projecter.MIN(field, alias);
		return this;
	}
	
	public ProjectedWrapper SUM(String field,String alias){
		projecter.SUM(field, alias);
		return this;
	}
	
	public ProjectedWrapper GROUPBY(String field){
		projecter.GROUPBY(field);
		return this;
	}
	
	public ProjectedWrapper UCASE(String field,String alias){
		projecter.UCASE(field, alias);
		return this;
	}
	
	public ProjectedWrapper LCASE(String field,String alias){
		projecter.LCASE(field, alias);
		return this;
	}
	
	public ProjectedWrapper MID(String field,long start,long end,String alias){
		projecter.MID(field, start, end, alias);
		return this;
	}
	
	public ProjectedWrapper LEN(String field,String alias){
		projecter.LEN(field, alias);
		return this;
	}
	
	public ProjectedWrapper ROUND(String field,int decimals,String alias){
		projecter.ROUND(field, decimals, alias);
		return this;
	}
	
	public ProjectedWrapper NOW(String alias){
		projecter.NOW(alias);
		return this;
	}
	
	public ProjectedWrapper FORMAT(String field,String format,String alias){
		projecter.FORMAT(field, format, alias);
		return this;
	}

	//only get \ no set
	public StringBuilder getProjection() {
		return projection;
	}
}
