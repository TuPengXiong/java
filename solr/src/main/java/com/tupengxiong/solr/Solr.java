package com.tupengxiong.solr;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

/**
 * solr
 *
 */
public class Solr {

	private static SolrClient client;
	private static final String baseSolrUrl = "http://www.lovesher.com:8983/solr/gettingstarted/";

	public static synchronized SolrClient getSolrClient() {
		if (null == client) {
			client = new HttpSolrClient.Builder(baseSolrUrl).build();
		}
		return client;
	}

	/**
	 * 添加docs
	 */
	public static void addSolrDocs() {
		client = getSolrClient();
		System.out.println("已经获取链接--->>>" + client);
		Collection<SolrInputDocument> docs = new LinkedList<SolrInputDocument>();
		for (int i = 0; i < 10000; i++) {
			SolrInputDocument doc = new SolrInputDocument("id", "" + i, "username", "tu" + i, "password", "tu" + i);
			docs.add(doc);
		}
		try {
			client.add(docs);
			client.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public static void closeClient() {
		if (null != client) {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param collection
	 * @param params
	 * @return
	 */
	public static QueryResponse search(String collection, SolrParams params) {
		client = getSolrClient();
		QueryResponse resp = null;
		try {
			resp = client.query(collection, params);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	public static void main(String[] args) {
		try {
			addSolrDocs();
			System.out.println(" 添加docs 完毕");
			Map<String, String> map = new HashMap<String, String>();
			// 查询所有
			map.put("q", "*:*");
			// 排序 id降序
			map.put("sort", "id desc");
			
			//分页查询
			map.put("start", "0");
			map.put("rows", "5");
			
			SolrParams params = new MapSolrParams(map);
			QueryResponse response = search(null, params);
			for (int i = 0; i < response.getResults().getNumFound(); i++) {
				SolrDocument o = response.getResults().get(i);
				System.out.println(o.get("username") + "==>" + o.get("password"));
			}
			System.out.println(response.getResults().getNumFound());

		} catch (Exception e) {

		} finally {
			closeClient();
		}
	}

}
