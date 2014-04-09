package com.hg.ecommerce.dao.support.provider;

import java.sql.Types;
import java.util.Collection;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Black;

import com.hg.ecommerce.dao.support.IProjections;
import com.hg.ecommerce.dao.support.ISQLProvider;
@SuppressWarnings("unused")
public class MySQLProvider implements ISQLProvider {
	
	private StringBuilder SQL;
	
	private boolean isSelect = false;
	private boolean isUpdate = false;
	private boolean isDelete = false;
	private boolean isInsert = false;
	private boolean isFields = false;//work with insert
	private boolean isValues = false;//work with insert
	private boolean isWhere = false;
	private boolean isSet = false;//work with update
	private boolean isAnd = false;//work with where
	private boolean isOrder = false;//work with order
	private boolean isSetModel = false;
	
	/**
	 * 当使用selectAll 和 select(Projection)的时候，isProjected设置为true，控制ISQLProvider的fileds的使用
	 */
	private boolean isProjected = false;//work with select(Projection)
	
	private Object Model;
	
	public MySQLProvider(){
		this.SQL = new StringBuilder();
	}
	
	//this.SQL operation here
	
	@Override
	public ISQLProvider insert() {
		this.SQL = new StringBuilder();
		this.SQL.append(INSERT);
		resetStatus();//reset
		isInsert = true;
		return this;
	}
	
	@Override
	public ISQLProvider delete() {
		this.SQL = new StringBuilder();
		this.SQL.append(DELETE);
		resetStatus();
		isDelete = true;
		return this;
	}
	
	@Override
	public ISQLProvider values(Object...objects) {
		if(null!=objects && isInsert){
			if(!isValues){
				this.SQL.append(VALUES).append(LP);
				for(Object object : objects){
					if(object.equals(NULL)){
						this.SQL.append(Types.NULL).append(COMMA);
					}else{
						this.SQL.append(QUOTE).append(object).append(QUOTE).append(COMMA);
					}
					
				}
				this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
				this.SQL.append(RP);
				isValues = true;
			}else{
				for(Object object : objects){
					if(object.equals(NULL)){
						this.SQL.insert(this.SQL.lastIndexOf(RP), COMMA+Types.NULL);
					}else{
						this.SQL.insert(this.SQL.lastIndexOf(RP), COMMA+QUOTE+object+QUOTE);
					}
					
				}
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider values(Collection<Object> objects){
		if(null!=objects && isInsert){
			if(!isValues){
				this.SQL.append(VALUES).append(LP);
				for(Object object : objects){
					if(object.equals(NULL)){
						this.SQL.append(Types.NULL).append(COMMA);
					}else{
						this.SQL.append(QUOTE).append(object).append(QUOTE).append(COMMA);
					}
					
				}
				this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
				this.SQL.append(RP);
				isValues = true;
			}else{
				for(Object object : objects){
					if(object.equals(NULL)){
						this.SQL.insert(this.SQL.lastIndexOf(RP), COMMA+Types.NULL);
					}else{
						this.SQL.insert(this.SQL.lastIndexOf(RP), COMMA+QUOTE+object+QUOTE);
					}
					
				}
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider update() {
		this.SQL = new StringBuilder();
		this.SQL.append(UPDATE);
		resetStatus();
		isUpdate = true;
		return this;
	}
	
	@Override
	public ISQLProvider select() {
		this.SQL = new StringBuilder();
		this.SQL.append(SELECT);
		resetStatus();
		isSelect = true;
		return this;
	}
	
	@Override
	public ISQLProvider select(IProjections iProjections) {
		if(null!=iProjections){
			this.SQL.append(SELECT).append(iProjections.getProjection()).append(BLANK);
			isSelect = true;
			isProjected = true;
		}
		return null;
	}
	
	@Override
	public ISQLProvider selectAll(){
		this.SQL = new StringBuilder();
		this.SQL.append(SELECT).append(MULTI).append(FROM);
		isSelect = true;
		isProjected = true;
		return this;
	}
	
	@Override
	public ISQLProvider distinct() {
		if(isSelect){
			this.SQL.append(DISTINCT);
		}
		return this;
	}
	
	@Override
	public ISQLProvider distinct(Object field) {
		if(isSelect){
			if(null!=field){
				return this.distinct();
			}else{
				this.SQL.append(DISTINCT).append(field);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider fields(Object... objects) {
		if(null!=objects){
			if(isSelect && !isProjected){
				if(!isFields){
					for(Object object : objects){
						this.SQL.append(BLANK).append(object.toString()).append(COMMA);
					}
					this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
					this.SQL.append(FROM);
					isFields = true;
				}else{
					for(Object object : objects){
						this.SQL.insert(this.SQL.indexOf(FROM),COMMA+object.toString());
					}
					isFields = true;
				}
			}else if(isInsert){
				if(!isFields){
					this.SQL.append(LP);
					for(Object object : objects){
						this.SQL.append(BLANK).append(object).append(COMMA);
					}
					this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
					this.SQL.append(RP);
					isFields = true;
				}else{
					if(-1!=this.SQL.indexOf(RP)){
						for(Object object : objects){
							this.SQL.insert(this.SQL.indexOf(RP), COMMA+object);
						}
					}
				}
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider fields(Collection<Object> objects) {
		if(null!=objects){
			if(isSelect && !isProjected){
				if(!isFields){
					for(Object object : objects){
						this.SQL.append(BLANK).append(object).append(COMMA);
					}
					this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
					this.SQL.append(FROM);
					isFields = true;
				}else{
					for(Object object : objects){
						this.SQL.insert(this.SQL.indexOf(FROM),COMMA+object.toString());
					}
					isFields = true;
				}
			}else if(isInsert){
				if(!isFields){
					this.SQL.append(LP);
					for(Object object : objects){
						
						this.SQL.append(BLANK).append(object).append(COMMA);
					}
					this.SQL = new StringBuilder(this.SQL.substring(0, this.SQL.lastIndexOf(COMMA)));
					this.SQL.append(RP);
					isFields = true;
				}else{
					if(-1!=this.SQL.indexOf(RP)){
						for(Object object : objects){
							this.SQL.insert(this.SQL.indexOf(RP), COMMA+object);
						}
					}
				}
			}
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
		this.SQL.append(field).append(EQ).append(field).append(PLUS).append(value);
		return this;
	}
	
	@Override
	public ISQLProvider dec(String field, long value) {
		this.SQL.append(field).append(EQ).append(field).append(SUB).append(value);
		return this;
	}
	
	@Override
	public ISQLProvider where() {
		if(isSelect||isUpdate||isDelete){
			this.SQL.append(WHERE);
		}
		isWhere = true;
		return this;
	}
	
	@Override
	public ISQLProvider and(ISQLProvider isqlProvider) {
		if(null!=isqlProvider){
			if(!isAnd){
				this.SQL.append(LP).append(isqlProvider.getSQL()).append(RP);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(LP).append(isqlProvider.getSQL()).append(RP);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider and(ISQLProvider one, ISQLProvider another,AOR AOR) {
		if(null!=one && null!=another){
			if(!isAnd){
				this.SQL.append(LP).append(one.getSQL()).append(RP).append(AND).append(LP).append(another.getSQL()).append(RP);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(LP).append(LP).append(one.getSQL()).append(RP).append(AOR.getAor()).append(LP).append(another.getSQL()).append(RP).append(RP);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider or(ISQLProvider isqlProvider) {
		if(null!=isqlProvider){
			if(!isAnd){
				this.SQL.append(isqlProvider.getSQL());
				isAnd = true;
			}else{
				this.SQL.append(OR).append(LP).append(isqlProvider.getSQL()).append(RP);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider or(ISQLProvider one, ISQLProvider another,AOR AOR) {
		if(null!=one && null!=another){
			if(!isAnd){
				this.SQL.append(LP).append(LP).append(one.getSQL()).append(RP).append(OR).append(LP).append(another.getSQL()).append(RP).append(RP);
				isAnd = true;
			}else{
				this.SQL.append(OR).append(LP).append(LP).append(one.getSQL()).append(RP).append(AOR.getAor()).append(LP).append(another.getSQL()).append(RP).append(RP);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider eq(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(EQ).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider ne(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(NE).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(NE).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider gt(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(GT).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(GT).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider ge(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(GE).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(GE).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider lt(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(LT).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(LT).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider le(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(LE).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(LE).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider not(Object field, Object value) {
		if(null!=value){
			if(!isAnd){
				this.SQL.append(field).append(NOT).append(QUOTE).append(value).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(NOT).append(QUOTE).append(value).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider between(Object field, Object lvalue, Object rvalue) {
		if(null!=lvalue && null!=rvalue){
			if(!isAnd){
				this.SQL.append(field).append(BETWEEN).append(QUOTE).append(lvalue).append(QUOTE).append(AND).append(QUOTE).append(rvalue).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(BETWEEN).append(QUOTE).append(lvalue).append(QUOTE).append(AND).append(QUOTE).append(rvalue).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider in(Object field, Object... objects) {
		if(null!=objects){
			StringBuilder list = new StringBuilder();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuilder(list.substring(0, list.lastIndexOf(COMMA)));
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
	public ISQLProvider in(Object field, Collection<Object> objects) {
		if(null!=objects){
			StringBuilder list = new StringBuilder();
			list.append(LP);
			for(Object object : objects){
				list.append(QUOTE).append(object).append(QUOTE).append(COMMA);
			}
			list = new StringBuilder(list.substring(0, list.lastIndexOf(COMMA)));
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
	public ISQLProvider notIn(Object field, Object... objects) {
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
	public ISQLProvider notIn(Object field, Collection<Object> objects) {
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
	public ISQLProvider like(Object field, Object regex) {
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
	public ISQLProvider notLike(Object field, Object regex) {
		if(null!=regex){
			if(!isAnd){
				this.SQL.append(field).append(QUOTE).append(regex).append(QUOTE);
				isAnd = true;
			}else{
				this.SQL.append(AND).append(field).append(QUOTE).append(regex).append(QUOTE);
			}
		}
		return this;
	}
	
	@Override
	public ISQLProvider isNull(Object field) {
		if(!isAnd){
			this.SQL.append(field).append(IS_NULL);
			isAnd = true;
		}else{
			this.SQL.append(AND).append(field).append(IS_NULL);
		}
		return this;
	}
	
	@Override
	public ISQLProvider isNotNull(Object field) {
		if(!isAnd){
			this.SQL.append(field).append(IS_NOT_NULL);
			isAnd = true;
		}else{
			this.SQL.append(AND).append(field).append(IS_NOT_NULL);
		}
		return this;
	}
	
	@Override
	public ISQLProvider orderBy(Object field,SORT sort) {
		if(isSelect){
			if(!isOrder){
				this.SQL.append(ORDER).append(field).append(sort.getSort());
				isOrder = true;
			}else{
				this.SQL.append(COMMA).append(field).append(sort.getSort());
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
	public ISQLProvider HAVING(IProjections iProjections) {
		this.SQL.append(HAVING).append(iProjections.getProjection()).append(BLANK);
		return this;
	}
	
	@Override
	public String getSQL() {
		return this.SQL.toString();
	}
	
	
	//get & set
	@Override
	public void setModel(Object model) {
		if(!isSetModel){
			this.Model = model;
			if(isInsert){
				this.SQL.insert(this.SQL.indexOf(INSERT)+INSERT.length()+1, this.Model+BLANK);
				this.isSetModel = true;
			}else if(isDelete){
				this.SQL.insert(this.SQL.indexOf(DELETE)+DELETE.length()+1, this.Model+BLANK);
				this.isSetModel = true;
			}else if(isUpdate){
				this.SQL.insert(this.SQL.indexOf(UPDATE)+UPDATE.length(), this.Model+BLANK+SET);
				this.isSetModel = true;
			}else if(isSelect){
				if(-1!=this.SQL.indexOf(FROM)){
					this.SQL.append(BLANK).append(BLANK);
					this.SQL.insert(this.SQL.indexOf(FROM)+FROM.length()+1, this.Model+BLANK);
				}else if(-1!=this.SQL.indexOf(WHERE)){
					this.SQL.insert(this.SQL.indexOf(WHERE), FROM+this.Model+BLANK);
				}else{
					this.SQL.append(FROM).append(this.Model);
				}
				this.isSetModel = true;
			}
		}
	}
	
	@Override
	public Object getModel() {
		return Model;
	}

	/**
	 * 重置sql状态，防止用于复用一个实例而产生错误
	 * 用于insert/delete/select/update之后
	 */
	public void resetStatus(){
		this.isAnd = false;
		this.isDelete = false;
		this.isFields = false;
		this.isInsert = false;
		this.isOrder = false;
		this.isProjected = false;
		this.isSelect = false;
		this.isSet = false;
		this.isUpdate = false;
		this.isValues = false;
		this.isWhere = false;
		this.isSetModel = false;
	}


	
	

	



	




	
	
	
	
	

}
