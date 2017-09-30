package com.tupengxiong.elasticsearch.bean.base;

/**
 * @Title: BaseSearchBean.java 
 * @version V1.0
 * 
 */
public class BaseSearchBean {

	public enum BuilderType {
		/** 非分词查询（精确） **/
		termQuery,

		/** 范围 **/
		rangeQuery
	}

	public enum OptType {
		/** 与 **/
		must, /** 或 **/
		mustNot, /** 非 **/
		should
	}

	/** 范围查找 小于等于或小于 **/
	public enum LtType {
		lte, lt
	}

	/** 范围查找 大于等于或大于 **/
	public enum GtType {
		gte, gt
	}

	private BuilderType builderType;

	private OptType optType;

	private GtType gtType = GtType.gte;

	private LtType ltType = LtType.lte;

	public BaseSearchBean() {
	}

	/** 必传 **/
	public BaseSearchBean(BuilderType builderType, OptType optType) {
		this.builderType = builderType;
		this.optType = optType;
	}

	/**
	 * 字段名称
	 */
	private String propertyName;

	/**
	 * 字段值
	 */
	private Object propertyValue;

	/**
	 * 范围起点值
	 */
	private Object rangStart;

	/**
	 * 范围终点值
	 */
	private Object rangEnd;

	/**
	 * 时间的format yyyy-MM-dd HH:mm:ss
	 */
	private String format;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Object rangeBuilder() {
		return rangStart;
	}

	public Object getRangEnd() {
		return rangEnd;
	}

	public void setRangEnd(Object rangEnd) {
		this.rangEnd = rangEnd;
	}

	public OptType getOptType() {
		return optType;
	}

	public void setOptType(OptType optType) {
		this.optType = optType;
	}

	public BuilderType getBuilderType() {
		return builderType;
	}

	public void setBuilderType(BuilderType builderType) {
		this.builderType = builderType;
	}

	public GtType getGtType() {
		return gtType;
	}

	public void setGtType(GtType gtType) {
		this.gtType = gtType;
	}

	public LtType getLtType() {
		return ltType;
	}

	public void setLtType(LtType ltType) {
		this.ltType = ltType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Object getRangStart() {
		return rangStart;
	}

	public void setRangStart(Object rangStart) {
		this.rangStart = rangStart;
	}
}
