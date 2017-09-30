package com.tupengxiong.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsSearch {

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
	
	/** 排序 **/
	public enum OrderDirection {
		ASC, DESC
	}

	/** 范围查找 大于等于或大于 **/
	public enum GtType {
		gte, gt
	}
	
	/** 
	 * 范围起点还是终点
	 */
	public enum RangeType {
		start, end
	}

	public BuilderType builderType() default BuilderType.termQuery;

	public OptType optType() default OptType.must;

	public GtType gtType() default GtType.gte;

	public LtType ltType() default LtType.lte;
	
	public RangeType rangeType() default RangeType.start;

	/**
	 * 是否启用搜索
	 */
	public boolean enableSearch() default true;
	
	/**
	 * 是否启用排序
	 */
	public boolean enableSort() default true;
	
	/**
	 * 字段名称
	 */
	public String propertyName() default "";

	/**
	 * 时间的format 默认是毫秒
	 */
	public String format() default "strict_date_optional_time||epoch_millis";
	
	/**
	 * 排序方式
	 */
	public OrderDirection orderDirection() default OrderDirection.DESC;
	
	/**
	 * 属性是否排序
	 */
	public boolean order() default false;
}
