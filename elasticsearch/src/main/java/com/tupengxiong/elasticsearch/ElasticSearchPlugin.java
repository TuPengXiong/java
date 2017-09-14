package com.tupengxiong.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.alibaba.fastjson.JSON;

/**
 * 
 * ClassName: ElasticSearchPlugin <br/>
 * date: 2017年9月13日 下午2:00:17 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 */
public class ElasticSearchPlugin {

	private static Logger logger = Logger.getLogger(ElasticSearchPlugin.class);
	private static TransportClient client = null;

	static {
		createTransportClient();
	}

	/**
	 * 创建TransportClient
	 */
	public static void createTransportClient() {
		Settings settings = Settings.settingsBuilder().put("cluster.name", "es-cluster")
				// .put("client.transport.ignore_cluster_name", true)
				.put("client.transport.ping_timeout", "10s") // 10s
				// .put("client.transport.nodes_sampler_interval", "10s")
				.put("client.transport.sniff", true)// 嗅探整个集群的状态
				.build();
		client = TransportClient.builder().settings(settings).build();
		try {
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.18.116"), 9300));
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.18.117"), 9300));
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.18.118"), 9300));
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
			logger.error("createTransportClient error");
		}
	}

	/**
	 * 关闭
	 */
	public void closeTransportClient() {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * json格式创建索引 createIndexJson
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public boolean createIndexJson(ConvertBean bean) {
		String json = JSON.toJSONString(bean.getParamsMap());
		try {
			IndexResponse response = client
					.prepareIndex(bean.getdBname(), bean.getTableName(), bean.getPrimaryKey().toString())
					.setRefresh(true).setSource(json).get();
			logger.debug("createIndexJson:" + JSON.toJSONString(response));
			return response.isCreated();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("createIndexJson ERROR:" + JSON.toJSONString(bean));
		}
		return Boolean.FALSE;
	}

	/**
	 * Map格式创建索引 createIndexMap
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public boolean createIndexMap(ConvertBean bean) {
		try {
			IndexResponse response = client
					.prepareIndex(bean.getdBname(), bean.getTableName(), bean.getPrimaryKey().toString())
					.setRefresh(true).setSource(bean.getParamsMap()).get();
			logger.debug("createIndexMap:" + JSON.toJSONString(response));
			return response.isCreated();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("createIndexMap ERROR:" + JSON.toJSONString(bean));
		}
		return Boolean.FALSE;
	}

	/**
	 * Builder格式创建索引 createIndexBuilder
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public IndexResponse createIndexBuilder(ConvertBean bean) {
		// TODO

		/*
		 * IndexResponse response = null; try { response =
		 * client.prepareIndex(bean.getdBname(), bean.getTableName())
		 * .setSource( XContentFactory.jsonBuilder() .startObject()
		 * .field("user", "kimchy") .field("postDate", new
		 * Date()).field("message", "trying out Elasticsearch -- testBuilder")
		 * .field("tpx", "tpx").endObject() ) .get(); } catch (IOException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } return
		 * response;
		 */
		return null;
	}

	/**
	 * 删除数据 delete
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public boolean delete(ConvertBean bean) {
		try {
			DeleteResponse response = client
					.prepareDelete(bean.getdBname(), bean.getTableName(), bean.getPrimaryKey().toString()).get();
			logger.debug("delete:" + JSON.toJSONString(response));
			return response.isFound();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("delete ERROR:" + JSON.toJSONString(bean));
		}
		return Boolean.FALSE;
	}

	/**
	 * 更新数据 update
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public boolean update(ConvertBean bean) {

		try {
			UpdateResponse response = client
					.prepareUpdate(bean.getdBname(), bean.getTableName(), bean.getPrimaryKey().toString())
					.setDoc(bean.getParamsMap()).get();
			logger.debug("update:" + JSON.toJSONString(response));
			return response.isCreated();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			logger.error("update ERROR:" + JSON.toJSONString(bean));
		}
		return Boolean.FALSE;
	}

	/**
	 * 搜索数据 search
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	public SearchResponse search(String[] dbNames, String[] tableNames, Map<String, Object> mustQuery,
			Map<String, Object> filter, Map<String, Object> mustNotQuery, Map<String, Object> shouldQuery,
			RangeQueryBuilder rangeQueryBuilder, Integer from, Integer size, String sortFiled, String sortOrder) {
		if (null == dbNames) {
			return null;
		}
		SearchRequestBuilder seBuilder = client.prepareSearch(dbNames);
		if (null != tableNames && tableNames.length > 0) {
			seBuilder.setTypes(tableNames);
		}
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (null != rangeQueryBuilder) {
			queryBuilder.must(rangeQueryBuilder);
		}
		if (null != mustQuery && mustQuery.size() > 0) {
			for (String key : mustQuery.keySet()) {
				queryBuilder.must(QueryBuilders.matchQuery(key, mustQuery.get(key)));
			}
		}
		if (null != mustNotQuery && mustNotQuery.size() > 0) {
			for (String key : mustNotQuery.keySet()) {
				queryBuilder.mustNot(QueryBuilders.matchQuery(key, mustNotQuery.get(key)));
			}
		}

		if (null != shouldQuery && shouldQuery.size() > 0) {
			for (String key : shouldQuery.keySet()) {
				queryBuilder.should(QueryBuilders.matchQuery(key, shouldQuery.get(key)));
			}
		}
		if (null != filter && filter.size() > 0) {
			for (String key : filter.keySet()) {
				queryBuilder.filter(QueryBuilders.matchQuery(key, filter.get(key)));
			}
		}
		if (null != from) {
			seBuilder.setFrom(from);
		}
		if (null != size) {
			seBuilder.setSize(size);
		}
		seBuilder.setQuery(queryBuilder);
		if (null != sortFiled && null != sortOrder
				&& (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
			seBuilder.addSort(sortFiled, SortOrder.valueOf(sortOrder.toUpperCase()));
		}
		seBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		seBuilder.setExplain(false);// 不获取解释信息
		SearchResponse response = seBuilder.get();
		logger.debug(JSON.toJSONString(response));
		return response;
	}

	/**
	 * 搜索数据(排序无关 按 index 顺序返回) searchByScroll
	 * 
	 * @author tupengxiong
	 * @param bean
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("deprecation")
	public SearchResponse searchByScroll(String[] dbNames, String[] tableNames, Map<String, Object> mustQuery,
			Map<String, Object> filter, Map<String, Object> mustNotQuery, Map<String, Object> shouldQuery,
			RangeQueryBuilder rangeQueryBuilder, Long sec, Integer pageSize) {
		if (null == dbNames) {
			return null;
		}
		// 初始化时只返回 _scroll_id，没有具体的 hits 结果
		SearchRequestBuilder seBuilder = client.prepareSearch(dbNames);
		if (sec == null) {
			sec = 180L;
		}
		seBuilder.setScroll(TimeValue.timeValueSeconds(sec));
		if (null != tableNames && tableNames.length > 0) {
			seBuilder.setTypes(tableNames);
		}
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (null != mustQuery && mustQuery.size() > 0) {
			for (String key : mustQuery.keySet()) {
				queryBuilder.must(QueryBuilders.matchQuery(key, mustQuery.get(key)));
			}
		}
		if (null != rangeQueryBuilder) {
			queryBuilder.must(rangeQueryBuilder);
		}
		if (null != mustNotQuery && mustNotQuery.size() > 0) {
			for (String key : mustNotQuery.keySet()) {
				queryBuilder.mustNot(QueryBuilders.matchQuery(key, mustNotQuery.get(key)));
			}
		}

		if (null != shouldQuery && shouldQuery.size() > 0) {
			for (String key : shouldQuery.keySet()) {
				queryBuilder.should(QueryBuilders.matchQuery(key, shouldQuery.get(key)));
			}
		}
		if (null != filter && filter.size() > 0) {
			for (String key : filter.keySet()) {
				queryBuilder.filter(QueryBuilders.matchQuery(key, filter.get(key)));
			}
		}
		// size 控制的是每个分片的返回的数据量而不是整个请求返回的数据量
		if (null != pageSize) {
			seBuilder.setSize(pageSize);
		}
		seBuilder.setQuery(queryBuilder);
		// 不获取数据
		seBuilder.setSearchType(SearchType.SCAN);
		// 不获取解释信息
		seBuilder.setExplain(false);
		SearchResponse scrollResponse = seBuilder.get();
		// 第一次不返回数据
		long count = scrollResponse.getHits().getTotalHits();
		logger.debug("count==>" + count);
		scrollResponse = client.prepareSearchScroll(scrollResponse.getScrollId()).get();
		logger.debug(JSON.toJSONString(scrollResponse));
		logger.debug("getTotalHits==>" + scrollResponse.getHits().getTotalHits());
		return scrollResponse;
	}
}
