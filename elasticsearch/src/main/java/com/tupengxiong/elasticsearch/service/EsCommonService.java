package com.tupengxiong.elasticsearch.service;

import com.tupengxiong.elasticsearch.bean.base.BaseSearch;
import com.tupengxiong.elasticsearch.bean.base.ElasticSearchParams;

public interface EsCommonService {

	/**
	 * es搜索通用
	 * 
	 * @Title: getElasticSearchParams
	 * @Description: es搜索通用
	 * @author tupx
	 * @date 2017年9月29日 下午4:24:59
	 * @version V1.0
	 */
	ElasticSearchParams getElasticSearchParams(Class<?> esSourceClass, BaseSearch baseSearch);
}
