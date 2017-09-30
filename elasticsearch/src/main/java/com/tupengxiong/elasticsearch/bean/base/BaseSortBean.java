package com.tupengxiong.elasticsearch.bean.base;

public class BaseSortBean {

	/**字段名字**/
	private String propertyName;
	
	private SortOrder sortOrder;
	
	public enum SortOrder{
		/** 升序**/
		ASC,
		
		/** 降序**/
		DESC
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
