package com.hg.ecommerce.dao.support;

import java.util.Collection;

public interface IProjections extends IOperators{

	//fields
	public IProjections fields(Object...objects);
	
	public IProjections fields(Collection<Object> objects);
	
	//projections  functions; usually be used for select
	public IProjections AVG(String field,String alias);
	
	public IProjections COUNT(String alias);
	
	public IProjections COUNT(String field,String alias);
	
	public IProjections FIRST(String field,String alias);
	
	public IProjections LAST(String field,String alias);
	
	public IProjections MAX(String field,String alias);
	
	public IProjections MIN(String field,String alias);
	
	public IProjections SUM(String field,String alias);
	
	public IProjections GROUPBY(String field);
	
	public IProjections UCASE(String field,String alias);
	
	public IProjections LCASE(String field,String alias);
	
	public IProjections MID(String field,long start,long end,String alias);
	
	public IProjections LEN(String field,String alias);
	
	public IProjections ROUND(String field,int decimals,String alias);
	
	public IProjections NOW(String alias);
	
	public IProjections FORMAT(String field,String format,String alias);
	
	//get projection
	public String getProjection();
}
