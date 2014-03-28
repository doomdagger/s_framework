package com.hg.ecommerce.dao.support.provider;

import java.util.Collection;

import com.hg.ecommerce.dao.support.ISQLProvider;
@SuppressWarnings("unused")
public class MySQLProvider implements ISQLProvider {
	
	private StringBuffer SQL;
	private boolean isSelect = false;
	private boolean isUpdate = false;
	private boolean isDelete = false;
	private boolean isInsert = false;
	private boolean isFields = false;
	private boolean isWhere = false;
	
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
		if(isInsert || isSelect){
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
			this.SQL.append(SET).append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
		}
		return this;
	}

	@Override
	public ISQLProvider inc(String field, long value) {
		if(null!=field && isUpdate){
			//this.SQL.append(b)
		}
		return this;
	}

	@Override
	public ISQLProvider where() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider ne(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider gt(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider ge(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider lt(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider le(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider not(String field, Object value) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider between(String field, Object lvalue, Object rvalue) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider in(String field, Object... objects) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider in(String field, Collection<Object> objects) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider notIn(String field, Object... objects) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider notIn(String field, Collection<Object> objects) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider like(String field, Object regex) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider notLike(String filed, Object regex) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider isNull(String field) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider isNotNull(String field) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider orderBy() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ISQLProvider limit() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getSQL() {
		return this.SQL.toString();
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}





	
	

}
