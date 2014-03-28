package com.hg.ecommerce.dao.support.provider;

import java.util.Collection;

import com.hg.ecommerce.dao.support.ISQLProvider;
import com.hg.ecommerce.dao.support.ISQLProvider.Sort;
@SuppressWarnings("unused")
public class MySQLProvider implements ISQLProvider {
	
	private StringBuffer SQL;
	private boolean isSelect = false;
	private boolean isUpdate = false;
	private boolean isDelete = false;
	private boolean isInsert = false;
	private boolean isFields = false;
	private boolean isWhere = false;
	private boolean isSet = false;//work with update
	private boolean isAnd = false;//work with where
	private boolean isOrder = false;//work with order
	
	private String Model;
	
	private MySQLProvider(String Model){
		this.setModel(Model);
		this.SQL = new StringBuffer();
	}
	
	public static MySQLProvider instance(String Model){
		return new MySQLProvider(Model);
	}
	
	
	//this.SQL operation here
	
	@Override
	public ISQLProvider insert() {
		this.SQL.append(INSERT).append(this.Model);
		isInsert = true;
		return this;
	}
	
	@Override
	public ISQLProvider delete() {
		this.SQL.append(DELETE).append(this.Model);
		isDelete = true;
		return this;
	}
	
	@Override
	public ISQLProvider values(Object...objects) {
		if(null!=objects && isInsert){
			this.SQL.append(VALUES).append(LP);
			for(Object object : objects){
				this.SQL.append(QUOTE).append(object).append(QUOTE);
				this.SQL.append(COMMA);
			}
			this.SQL = new StringBuffer(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
			this.SQL.append(RP);
		}
		return this;
	}
	
	@Override
	public ISQLProvider values(Collection<Object> objects){
		if(null!=objects && isInsert){
			this.SQL.append(VALUES).append(LP);
			for(Object object : objects){
				this.SQL.append(QUOTE).append(object).append(QUOTE);
				this.SQL.append(COMMA);
			}
			this.SQL = new StringBuffer(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
			this.SQL.append(RP);
		}
		return this;
	}
	
	@Override
	public ISQLProvider update() {
		this.SQL.append(UPDATE).append(this.Model).append(SET);
		isUpdate = true;
		return this;
	}
	
	@Override
	public ISQLProvider select() {
		this.SQL.append(SELECT);
		isSelect = true;
		return this;
	}
	
	@Override
	public ISQLProvider fields(Object... objects) {
		if(null!=objects && (isInsert || isSelect)){
			this.SQL.append(LP);
			for(Object object : objects){
				this.SQL.append(object).append(COMMA);
			}
			this.SQL = new StringBuffer(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
			this.SQL.append(RP);
		}
		return this;
	}
	
	@Override
	public ISQLProvider fields(Collection<Object> objects) {
		if(null!=objects && (isInsert || isSelect)){
			this.SQL.append(LP);
			for(Object object : objects){
				this.SQL.append(object).append(COMMA);
			}
			this.SQL = new StringBuffer(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
			this.SQL.append(RP);
		}
		return this;
	}
	
	@Override
	public ISQLProvider set(String field, Object value) {
		if(null!=field && null!=value && isUpdate){
			if(isSet){
				this.SQL.append(COMMA).append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
			}else{
				this.SQL.append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
				isSet = true;
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider inc(String field, long value) {
		if(null!=field && isUpdate){
			if(isSet){
				this.SQL.append(COMMA).append(field).append(QUOTE).append(value).append(QUOTE);
			}else{
				this.SQL.append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
				isSet = true;
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider where() {
		if(isSelect||isUpdate||isDelete){
			this.SQL.append(WHERE);
		}
		return this;
	}
	
	@Override
	public ISQLProvider and(ISQLProvider isqlProvider) {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ISQLProvider and(ISQLProvider one, ISQLProvider another) {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ISQLProvider or(ISQLProvider isqlProvider) {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ISQLProvider or(ISQLProvider one, ISQLProvider another) {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ISQLProvider eq(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(EQ).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(EQ).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider ne(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(NE).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(NE).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider gt(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(GT).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(GT).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider ge(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(GE).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(GE).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider lt(String field, Object value) {
		if(null!=value){
			if(isAnd){
				this.SQL.append(field).append(LT).append(value);
				isAnd = true;
			}else{
				this.SQL.append(isAnd).append(field).append(LT).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider le(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(LE).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(LE).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider not(String field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(NOT).append(value);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(NOT).append(value);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider between(String field, Object lvalue, Object rvalue) {
		if(null!=lvalue && null!=rvalue){
			if(!isAnd){
				this.SQL.append(field).append(BETWEEN).append(lvalue).append(AND).append(rvalue);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(BETWEEN).append(lvalue).append(AND).append(rvalue);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider in(String field, Object... objects) {
		if(null!=objects){
			StringBuffer list = new StringBuffer();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuffer(list.substring(0, list.lastIndexOf(COMMA)));
			list.append(RP);
			if(!isAnd){
				this.SQL.append(IN);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(IN);
			}
			this.SQL.append(list);
		}
		return this;
	}
	
	@Override
	public ISQLProvider in(String field, Collection<Object> objects) {
		if(null!=objects){
			StringBuffer list = new StringBuffer();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuffer(list.substring(0, list.lastIndexOf(COMMA)));
			list.append(RP);
			if(!isAnd){
				this.SQL.append(IN);
			}else{
				this.SQL.append(AND).append(IN);
			}
			this.SQL.append(list);
		}
		return this;
	}
	
	@Override
	public ISQLProvider notIn(String field, Object... objects) {
		if(null!=objects){
			StringBuffer list = new StringBuffer();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuffer(list.substring(0, list.lastIndexOf(COMMA)));
			list.append(RP);
			if(!isAnd){
				this.SQL.append(NOT_IN);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(NOT_IN);
			}
			this.SQL.append(list);
		}
		return this;
	}
	
	@Override
	public ISQLProvider notIn(String field, Collection<Object> objects) {
		if(null!=objects){
			StringBuffer list = new StringBuffer();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuffer(list.substring(0, list.lastIndexOf(COMMA)));
			list.append(RP);
			if(!isAnd){
				this.SQL.append(NOT_IN);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(NOT_IN);
			}
			this.SQL.append(NOT_IN);
			this.SQL.append(list);
		}
		return this;
	}
	
	@Override
	public ISQLProvider like(String field, Object regex) {
		if(null!=regex){
			if(!isAnd){
				this.SQL.append(field).append(LIKE).append(QUOTE).append(regex).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(LIKE).append(QUOTE).append(regex).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider notLike(String filed, Object regex) {
		if(null!=regex){
			if(!isAnd){
				this.SQL.append(filed).append(QUOTE).append(regex).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(filed).append(QUOTE).append(regex).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider isNull(String field) {
		if(!isAnd){
			this.SQL.append(field).append(IS_NULL);
			isAnd = true;
		}else{
			this.SQL.append(AND).append(field).append(IS_NULL);
		}
		return this;
	}
	
	@Override
	public ISQLProvider isNotNull(String field) {
		if(!isAnd){
			this.SQL.append(field).append(IS_NOT_NULL);
			isAnd = true;
		}else{
			this.SQL.append(AND).append(field).append(IS_NOT_NULL);
		}
		return this;
	}
	
	@Override
	public ISQLProvider orderBy(String field,Sort sort) {
		if(isSelect){
			if(!isOrder){
				this.SQL.append(ORDER).append(field).append(sort);
				isOrder = true;
			}else{
				this.SQL.append(COMMA).append(field).append(sort);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider limit(int start,int length) {
		if(isSelect){
			this.SQL.append(LIMIT).append(start).append(COMMA).append(length);
		}
		return this;
	}
	
	@Override
	public String getSQL() {
		return this.SQL.toString();
	}
	
	
	//get & set
	public String getModel() {
		return Model;
	}
	
	public void setModel(String model) {
		Model = model;
	}
	
	
	

}
