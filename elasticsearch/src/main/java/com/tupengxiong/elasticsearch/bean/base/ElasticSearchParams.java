package com.tupengxiong.elasticsearch.bean.base;

import java.util.List;

/**
 * @Title: ElasticSearchParams.java
 * 
 * @version V1.0
 * 
 */
public class ElasticSearchParams {

	/** 集群名称 **/
	private String clusterName;

	/** ES type **/
	private String type;

	/** ES index **/
	private String index;
	
	/**ES 分页中的from 相当于mysql的limit from,10**/
	private Integer from;
	
	/**ES 分页中的size 相当于mysql的limit 0,10 size=10**/
	private Integer size;
	
	/** 求和字段**/
	private String sumProperty;

	/** 查询条件**/
	private List<BaseSearchBean> baseSearchBeans;
	
	/**排序条件**/
	private List<BaseSortBean> baseSortBeans;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<BaseSearchBean> getBaseSearchBeans() {
		return baseSearchBeans;
	}

	public void setBaseSearchBeans(List<BaseSearchBean> baseSearchBeans) {
		this.baseSearchBeans = baseSearchBeans;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<BaseSortBean> getBaseSortBeans() {
		return baseSortBeans;
	}

	public void setBaseSortBeans(List<BaseSortBean> baseSortBeans) {
		this.baseSortBeans = baseSortBeans;
	}

	public String getSumProperty() {
		return sumProperty;
	}

	public void setSumProperty(String sumProperty) {
		this.sumProperty = sumProperty;
	}
}
