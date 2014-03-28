package com.hg.ecommerce.dao.support;

import java.util.Collection;


public interface ISQLProvider {
	
	/**
	 * Database common operation,you can override in subclass,you also can add new operation in subclass
	 */
	public static final String EQ = " = ";
	public static final String NE = " <> ";
	public static final String MOD = " % ";
	public static final String BIT_LEFT = " << ";
	public static final String BIT_RIGHT = " >> ";
	public static final String BIT_AND = " & ";
	public static final String BIT_OR = " | ";
	public static final String BIT_XOR = " ^ ";
	public static final String LOG_AND = " && ";
	public static final String LOG_OR = " || ";
	public static final String MULTI = " * ";
	public static final String PLUS = " + ";
	public static final String SUB = " - ";
	public static final String DIV = " / ";
	public static final String LT = " < ";
	public static final String LE = " <= ";
	public static final String GT = " > ";
	public static final String GE = " >= ";
	public static final String BETWEEN = " BETWEEN ";
	public static final String IS = " IS ";
	public static final String NOT = " NOT ";
	public static final String IS_NULL = " IS NULL ";
	public static final String IS_NOT = " IS_NOT ";
	public static final String IS_NOT_NULL = "IS NOT NULL";
	public static final String LIKE = " LIKE ";
	public static final String NOT_LIKE = " NOT LIKE ";
	//key words
	public static final String INSERT = " INSERT INTO ";//select
	public static final String VALUES = " VALUES ";
	public static final String DELETE = " DELETE ";//delete
	public static final String UPDATE = " UPDATE ";//update
	public static final String SET = " SET ";
	public static final String SELECT = " SELECT ";//select
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";//where
	public static final String AND = " AND ";
	public static final String ORDER = " ORDER BY ";
	public static final String DESC = " DESC ";
	public static final String ASC = " ASC ";
	public static final String LIMIT = " LIMIT ";
	//syntax operators
	public static final String LP = " ( ";//left parentheses
	public static final String RP = " ) ";//right parenthesess
	public static final String COMMA = ",";
	public static final String QUOTE = "'";//quotation 
	
	
	
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
	
	//select / set fields
	public ISQLProvider select();
	
	public ISQLProvider fields(Object...objects);
	
	public ISQLProvider fields(Collection<Object> objects);
	
	//where
	public ISQLProvider where();//static

	public ISQLProvider and(ISQLProvider isqlProvider);//static
	
	public ISQLProvider and(ISQLProvider one,ISQLProvider another);//static
	
	public ISQLProvider or(ISQLProvider isqlProvider);//static
	
	public ISQLProvider or(ISQLProvider one,ISQLProvider another);//static
	
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
	public ISQLProvider orderBy();
	//pageable
	public ISQLProvider limit();
	//public get sql
	public String getSQL();
	
}
