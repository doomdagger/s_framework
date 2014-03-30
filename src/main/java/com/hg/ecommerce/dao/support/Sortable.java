package com.hg.ecommerce.dao.support;

import com.hg.ecommerce.dao.support.IOperators.SORT;

public class Sortable {
	
	private String field;
	private SORT sort;
	
	private Sortable(String field,SORT sort){
		this.setSort(sort);
	}
	
	public static Sortable inSort(String field,SORT sort){
		return new Sortable(field,sort);
	}

	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}

	public SORT getSort() {
		return sort;
	}

	public void setSort(SORT sort) {
		this.sort = sort;
	}
}
