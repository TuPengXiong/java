package com.tupengxiong.elasticsearch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tupengxiong.elasticsearch.annotation.EsSearch;
import com.tupengxiong.elasticsearch.annotation.EsSearch.RangeType;
import com.tupengxiong.elasticsearch.bean.base.BaseAnnotationResult;
import com.tupengxiong.elasticsearch.bean.base.BaseSearch;
import com.tupengxiong.elasticsearch.bean.base.BaseSearchBean;
import com.tupengxiong.elasticsearch.bean.base.BaseSortBean;
import com.tupengxiong.elasticsearch.bean.base.ElasticSearchParams;
import com.tupengxiong.elasticsearch.bean.base.BaseSearchBean.BuilderType;
import com.tupengxiong.elasticsearch.bean.base.BaseSearchBean.GtType;
import com.tupengxiong.elasticsearch.bean.base.BaseSearchBean.LtType;
import com.tupengxiong.elasticsearch.bean.base.BaseSearchBean.OptType;
import com.tupengxiong.elasticsearch.bean.base.BaseSortBean.SortOrder;
import com.tupengxiong.elasticsearch.util.DataBeanUtil;

public class EsCommonServiceImpl implements EsCommonService {

	private static Logger logger = Logger.getLogger(EsCommonServiceImpl.class);

	public ElasticSearchParams getElasticSearchParams(Class<?> esSourceClass, BaseSearch baseSearch) {
		ElasticSearchParams elasticSearchParams = new ElasticSearchParams();
		Map<String, BaseAnnotationResult> annotationResults = DataBeanUtil.getPropertiesAndValue(baseSearch);
		// 获取所有属性以及属性的类型
		Map<String, BaseAnnotationResult> filedSet = new HashMap<String, BaseAnnotationResult>();
		DataBeanUtil.getProperties(filedSet, esSourceClass);
		// 封装对象
		List<BaseSearchBean> baseSearchBeans = new ArrayList<BaseSearchBean>();
		List<BaseSortBean> baseSortBeans = new ArrayList<BaseSortBean>();
		for (BaseAnnotationResult result : annotationResults.values()) {
			if (null != result.getEsSearch()) {
				EsSearch esSearch = result.getEsSearch();
				String propertyName = null;
				// 是否设置了属性值
				if (!StringUtils.isEmpty(esSearch.propertyName())) {
					propertyName = esSearch.propertyName();
				} else {
					// 默认是属性名
					propertyName = result.getPropertyName();
				}
				// es元数据是否含有该字段
				if (!filedSet.containsKey(propertyName)) {
					continue;
				}
				// 排序
				if (esSearch.enableSort() && esSearch.order()) {
					BaseSortBean baseSortBean = new BaseSortBean();
					baseSortBean.setPropertyName(propertyName);
					baseSortBean.setSortOrder(SortOrder.valueOf(esSearch.orderDirection().name()));
					baseSortBeans.add(baseSortBean);
				}
				logger.debug(result.getPropertyName() + ":" + result.getPropertyValue() + ":"
						+ result.getPropertyClass().getName());
				// 没有设置值 --判定是否设置了排序
				if (esSearch.enableSearch() && null != result.getPropertyValue()) {
					BaseSearchBean baseSearchBean = new BaseSearchBean();
					baseSearchBean.setPropertyName(propertyName);
					if (result.getPropertyClass() == Date.class) {
						baseSearchBean.setPropertyValue(((Date) result.getPropertyValue()).getTime());
					} else {
						baseSearchBean.setPropertyValue(result.getPropertyValue());
					}
					baseSearchBean.setOptType(OptType.valueOf(esSearch.optType().name()));
					baseSearchBean.setBuilderType(BuilderType.valueOf(esSearch.builderType().name()));
					if (esSearch.builderType() == com.tupengxiong.elasticsearch.annotation.EsSearch.BuilderType.rangeQuery
							&& esSearch.rangeType() == RangeType.start) {
						baseSearchBean.setGtType(GtType.valueOf(esSearch.gtType().name()));
						baseSearchBean.setRangStart(baseSearchBean.getPropertyValue());
					} else if (esSearch.builderType() == com.tupengxiong.elasticsearch.annotation.EsSearch.BuilderType.rangeQuery
							&& esSearch.rangeType() == RangeType.end) {
						baseSearchBean.setLtType(LtType.valueOf(esSearch.ltType().name()));
						baseSearchBean.setRangEnd(baseSearchBean.getPropertyValue());
					}
					baseSearchBean.setFormat(esSearch.format());
					baseSearchBeans.add(baseSearchBean);
				}
			}
		}
		elasticSearchParams.setBaseSearchBeans(baseSearchBeans);
		elasticSearchParams.setBaseSortBeans(baseSortBeans);
		return elasticSearchParams;
	}
}
