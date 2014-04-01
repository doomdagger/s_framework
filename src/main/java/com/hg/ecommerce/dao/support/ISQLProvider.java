package com.hg.ecommerce.dao.support;

import java.util.Collection;

public interface ISQLProvider extends IOperators{
	
	//common sql combination method
	
	//insert
	public ISQLProvider insert();
	
	public ISQLProvider values(Object...objects);
	
	public ISQLProvider values(Collection<Object> objects);
	
	//delete
	public ISQLProvider delete();
	
	//update
	public ISQLProvider update();
	
	public ISQLProvider set(String field,Object value);
	
	public ISQLProvider inc(String field,long value);//plus value
	
	public ISQLProvider dec(String field,long value);//decrease value
	
	//select / set fields
	public ISQLProvider select();
	
	public ISQLProvider select(IProjections iProjections);
	
	public ISQLProvider selectAll();
	
	public ISQLProvider distinct();
	
	public ISQLProvider distinct(Object field);
	
	public ISQLProvider fields(Object...objects);
	
	public ISQLProvider fields(Collection<Object> objects);
	
	//where
	public ISQLProvider where();//static

	public ISQLProvider and(ISQLProvider isqlProvider);//static
	
	public ISQLProvider and(ISQLProvider one,ISQLProvider another,AOR AOR);//static
	
	public ISQLProvider or(ISQLProvider isqlProvider);//static
	
	public ISQLProvider or(ISQLProvider one,ISQLProvider another,AOR AOR);//static
	
	public ISQLProvider eq(Object field,Object value);
	
	public ISQLProvider ne(Object field,Object value);
	
	public ISQLProvider gt(Object field,Object value);
	
	public ISQLProvider ge(Object field,Object value);
	
	public ISQLProvider lt(Object field,Object value);
	
	public ISQLProvider le(Object field,Object value);
	
	public ISQLProvider not(Object field,Object value);
	
	public ISQLProvider between(Object field,Object lvalue,Object rvalue);
	
	public ISQLProvider in(Object field,Object...objects);
	
	public ISQLProvider in(Object field,Collection<Object> objects);
	
	public ISQLProvider notIn(Object field,Object...objects);
	
	public ISQLProvider notIn(Object field,Collection<Object> objects);
	
	public ISQLProvider like(Object field,Object regex);
	
	public ISQLProvider notLike(Object filed,Object regex);
	
	public ISQLProvider isNull(Object field);
	
	public ISQLProvider isNotNull(Object field);
	//sortable
	public ISQLProvider orderBy(Object field,SORT sort);
	//pageable
	public ISQLProvider limit(int start,int length);
	//function
	public ISQLProvider HAVING(IProjections iProjections);
	//public get sql
	public String getSQL();
	
	//base
	public void setModel(Object model);
	
	public Object getModel();
	
	
	

	
}
