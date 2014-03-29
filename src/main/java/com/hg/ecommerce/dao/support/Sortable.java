package com.hg.ecommerce.dao.support;

public class Sortable {
	
	public static final String ASCEND = "ASC";
	
	public static final String DESCND = "DESC";
	
	private String field;
	private Sort sort;
	
	private Sortable(String field,Sort sort){
		this.setSort(sort);
	}
	
	public enum Sort{
		ASC(0),DESC(1);
		int sort;
		Sort(int sort){
			this.sort = sort;
		}
	}
	
	public static Sortable inSort(String field,Sort sort){
		return new Sortable(field,sort);
	}

	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
}
