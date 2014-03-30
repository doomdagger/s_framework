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
	
	public ISQLProvider distinct(String field);
	
	public ISQLProvider fields(Object...objects);
	
	public ISQLProvider fields(Collection<Object> objects);
	
	//where
	public ISQLProvider where();//static

	public ISQLProvider and(ISQLProvider isqlProvider);//static
	
	public ISQLProvider and(ISQLProvider one,ISQLProvider another,AOR AOR);//static
	
	public ISQLProvider or(ISQLProvider isqlProvider);//static
	
	public ISQLProvider or(ISQLProvider one,ISQLProvider another,AOR AOR);//static
	
	public ISQLProvider eq(String field,Object value);
	
	public ISQLProvider ne(String field,Object value);
	
	public ISQLProvider gt(String field,Object value);
	
	public ISQLProvider ge(String field,Object value);
	
	public ISQLProvider lt(String field,Object value);
	
	public ISQLProvider le(String field,Object value);
	
	public ISQLProvider not(String field,Object value);
	
	public ISQLProvider between(String field,Object lvalue,Object rvalue);
	
	public ISQLProvider in(String field,Object...objects);
	
	public ISQLProvider in(String field,Collection<Object> objects);
	
	public ISQLProvider notIn(String field,Object...objects);
	
	public ISQLProvider notIn(String field,Collection<Object> objects);
	
	public ISQLProvider like(String field,Object regex);
	
	public ISQLProvider notLike(String filed,Object regex);
	
	public ISQLProvider isNull(String field);
	
	public ISQLProvider isNotNull(String field);
	//sortable
	public ISQLProvider orderBy(String field,SORT sort);
	//pageable
	public ISQLProvider limit(int start,int length);
	//function
	public ISQLProvider HAVING(IProjections iProjections);
	//public get sql
	public String getSQL();
	
	//base
	public void setModel(String model);
	
	public String getModel();
	
	
	

	
}
