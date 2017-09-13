package com.tupengxiong.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

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
					.setSource(json).get();
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
					.setSource(bean.getParamsMap()).get();
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
			logger.debug("createIndexMap:" + JSON.toJSONString(response));
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
			logger.debug("createIndexMap:" + JSON.toJSONString(response));
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
	public SearchResponse search(String dbName, String tableName, Map<String, Object> query, Map<String, Object> filter,
			int from, int size) {
		SearchResponse response = client.prepareSearch(dbName).setTypes(tableName)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(query) // Query
				.setPostFilter(filter) // Filter
				.setFrom(from). // from
				setSize(size). // 数量
				setExplain(true).execute().actionGet();
		logger.debug(JSON.toJSONString(response));
		return response;
	}
}
