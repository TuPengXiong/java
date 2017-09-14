/** 
 * Project Name:elasticsearch 
 * File Name:CanalDataToElasticsearch.java 
 * Package Name:com.tupengxiong.elasticsearch 
 * Date:2017年9月13日下午1:17:02 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.elasticsearch;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

/**
 * ClassName:CanalDataToElasticsearch <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年9月13日 下午1:17:02 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class CanalThread {

	private Logger logger = Logger.getLogger(CanalThread.class);
	private ElasticSearchPlugin elasticSearchPlugin = new ElasticSearchPlugin();

	/**
	 * 测试函数
	 * 
	 * @throws InterruptedException
	 */
	public void esThread() throws InterruptedException {
		// 创建链接
		CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.145.129", 11111),
				"example", "", "");
		// CanalConnector connector =
		// CanalConnectors.newClusterConnector("192.168.145.129:2181",
		// "example", "", "");
		int batchSize = 1000;
		int emptyCount = 0;
		try {
			connector.connect();
			connector.subscribe(".*\\..*");
			connector.rollback();
			int totalEmptyCount = 1000;
			while (emptyCount < totalEmptyCount) {
				Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
				long batchId = message.getId();
				int size = message.getEntries().size();
				if (batchId == -1 || size == 0) {
					emptyCount++;
					logger.info("empty count : " + emptyCount);
					try {
						/*
						 * if(emptyCount >= totalEmptyCount){
						 * Thread.sleep(10000); emptyCount=0;
						 * logger.info("休眠10s之后继续轮询~~~"); }else{
						 * Thread.sleep(1000); }
						 */
						if (emptyCount >= totalEmptyCount) {
							emptyCount = 0;
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					emptyCount = 0;
					List<Entry> entrys = message.getEntries();

					CountDownLatch threadSignal = new CountDownLatch(entrys.size());
					int cpuCount = Runtime.getRuntime().availableProcessors();
					ExecutorService executor = Executors.newFixedThreadPool(cpuCount);
					logger.info("线程开始执行~");
					for (Entry entry : entrys) {
						Runnable task = new ESThread(threadSignal, entry);
						executor.execute(task);
					}
					threadSignal.await();
					logger.info("所有线程执行结束~");
					executor.shutdown();
					// logger.info("线程是否关闭："+executor.isShutdown());
				}

				connector.ack(batchId); // 提交确认
				// connector.rollback(batchId); // 处理失败, 回滚数据
			}

		} finally {
			connector.disconnect();
		}

	}

	private class ESThread implements Runnable {
		private CountDownLatch threadSignal;
		private Entry entry;

		public ESThread(CountDownLatch threadSignal, Entry entry) {
			this.threadSignal = threadSignal;
			this.entry = entry;
		}

		public void run() {
			RowChange rowChage = null;
			try {
				if (entry.getEntryType() != EntryType.TRANSACTIONBEGIN
						&& entry.getEntryType() != EntryType.TRANSACTIONEND) {
					rowChage = RowChange.parseFrom(entry.getStoreValue());
				}
			} catch (Exception e) {
				throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
						e);
			}
			if (null != rowChage) {
				final EventType eventType = rowChage.getEventType();
				for (final RowData rowData : rowChage.getRowDatasList()) {
					translate(entry, rowData, eventType);
				}
			}
			threadSignal.countDown();
		}
	}

	public void translate(Entry entry, RowData rowData, EventType eventType) {
		if (eventType == EventType.DELETE) {// delete
			Column column_before = rowData.getBeforeColumns(0);
			ConvertBean convert = new ConvertBean();
			convert.setEventType(eventType);
			convert.setdBname(entry.getHeader().getSchemaName());
			convert.setTableName(entry.getHeader().getTableName());
			convert.setPrimaryKeyName(column_before.getName());
			convert.setPrimaryKey(column_before.getValue());
			logger.info(JSON.toJSONString(convert));
			elasticSearchPlugin.delete(convert);
		} else if (eventType == EventType.INSERT) {// insert
			List<Column> afterColumns = rowData.getAfterColumnsList();
			ConvertBean convert = new ConvertBean();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for (Column column : afterColumns) {
				paramsMap.put(column.getName(), column.getValue());
				convert.setParamsMap(paramsMap);
				if (column.getIsKey()) {
					convert.setPrimaryKey(column.getValue());
				}
			}
			convert.setEventType(eventType);
			convert.setdBname(entry.getHeader().getSchemaName());
			convert.setTableName(entry.getHeader().getTableName());
			logger.info(JSON.toJSONString(convert));
			elasticSearchPlugin.createIndexMap(convert);
		} else { // update
			logger.info(eventType.getNumber());
			List<Column> afterColumns = rowData.getAfterColumnsList();
			Column column_before = rowData.getBeforeColumns(0);
			ConvertBean convert = new ConvertBean();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for (Column column : afterColumns) {
				paramsMap.put(column.getName(), column.getValue());
				convert.setParamsMap(paramsMap);
			}
			convert.setEventType(eventType);
			convert.setdBname(entry.getHeader().getSchemaName());
			convert.setTableName(entry.getHeader().getTableName());
			convert.setPrimaryKeyName(column_before.getName());
			convert.setPrimaryKey(column_before.getValue());
			logger.info(JSON.toJSONString(convert));
			elasticSearchPlugin.update(convert);
		}
	}

	/**
	 * 测试函数
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void main() throws InterruptedException {
		/*
		 * CanalThread test = new CanalThread(); test.esThread();
		 */
		ElasticSearchPlugin elasticSearchPlugin = new ElasticSearchPlugin();
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("id", "17");
		query.put("name", "444");
		logger.info(elasticSearchPlugin.search("test", "test", query, null, null, null, 0, 1));
		elasticSearchPlugin.closeTransportClient();
	}

}
