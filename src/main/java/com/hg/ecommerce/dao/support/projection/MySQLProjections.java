package com.hg.ecommerce.dao.support.projection;


import com.hg.ecommerce.dao.support.IProjections;

public class MySQLProjections implements IProjections{
	
	private StringBuilder projection;
	
	private boolean isProjected = false; 
	
	public MySQLProjections(){
		this.projection = new StringBuilder();
	}
	
	//fields
	@Override
	public IProjections fields(Object... objects) {
		if(null!=objects){
			for(Object object : objects){
				this.projection.append(object).append(COMMA);
			}
			this.projection = new StringBuilder(this.projection.substring(0, this.projection.lastIndexOf(COMMA)));
			this.projection.append(FROM);
		}
		return this;
	}

	/*
	@Override
	public IProjections fields(Collection<Object> objects) {
		if(null!=objects){
			for(Object object : objects){
				this.projection.append(object).append(COMMA);
			}
			this.projection = new StringBuilder(this.projection.substring(0, this.projection.lastIndexOf(COMMA)));
			this.projection.append(FROM);
		}
		return this;
	}
	*/
	
	//functions operation work for select
	@Override
	public IProjections AVG(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(AVG).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(AVG).append(TLP).append(field).append(TRP).append(AS).append(AVG+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections COUNT(String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(COUNT).append(TLP).append(MULTI).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(COUNT).append(TLP).append(MULTI).append(TRP).append(AS).append(COUNT+"_").append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections COUNT(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(COUNT).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(COUNT).append(TLP).append(field).append(TRP).append(AS).append(COUNT+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections FIRST(String field, String alias) {
		/*
		if(null!=alias && !alias.equals("")){
			this.projection.append(FIRST).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(FIRST).append(TLP).append(field).append(TRP).append(AS).append(FIRST+"_"+field).append(BLANK);
		}
		*/
		return this;
	}

	@Override
	public IProjections LAST(String field, String alias) {
		/*
		if(null!=alias && !alias.equals("")){
			this.projection.append(LAST).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(LAST).append(TLP).append(field).append(TRP).append(AS).append(LAST+"_"+field).append(BLANK);
		}
		*/
		return this;
	}

	@Override
	public IProjections MAX(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(MAX).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(MAX).append(TLP).append(field).append(TRP).append(AS).append(MAX+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections MIN(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(MIN).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(MIN).append(TLP).append(field).append(TRP).append(AS).append(MIN+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections SUM(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(SUM).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(SUM).append(TLP).append(field).append(TRP).append(AS).append(SUM+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections GROUPBY(String field) {
		this.projection.append(GROUPBY).append(field).append(BLANK);
		return this;
	}

	@Override
	public IProjections UCASE(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(UCASE).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(UCASE).append(TLP).append(field).append(TRP).append(AS).append(UCASE+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections LCASE(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(LCASE).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(LCASE).append(TLP).append(field).append(TRP).append(AS).append(LCASE+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections MID(String field, long start, long end, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(MID).append(TLP).append(field).append(COMMA).append(start).append(COMMA).append(end).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(MID).append(TLP).append(field).append(COMMA).append(start).append(COMMA).append(end).append(TRP).append(AS).append(LCASE+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections LEN(String field, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(LEN).append(TLP).append(field).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(LEN).append(TLP).append(field).append(TRP).append(AS).append(LEN+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections ROUND(String field, int decimals, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(ROUND).append(TLP).append(field).append(COMMA).append(decimals).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(ROUND).append(TLP).append(field).append(COMMA).append(decimals).append(TRP).append(AS).append(ROUND+"_"+field).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections NOW(String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(NOW).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(NOW).append(AS).append("NOW_"+alias).append(BLANK);
		}
		return this;
	}

	@Override
	public IProjections FORMAT(String field, String format, String alias) {
		if(isProjected){
			this.projection.append(COMMA);
		}else{
			this.isProjected = true;
		}
		if(null!=alias && !alias.equals("")){
			this.projection.append(FORMAT).append(TLP).append(field).append(COMMA).append(format).append(TRP).append(AS).append(alias).append(BLANK);
		}else{
			this.projection.append(FORMAT).append(TLP).append(field).append(COMMA).append(format).append(TRP).append(AS).append(FORMAT+"_"+alias).append(BLANK);
		}
		return this;
	}
	
	@Override
	public String getProjection(){
		return this.projection.toString();
	}


}
