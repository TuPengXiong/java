package com.alibaba.otter.canal.sample;

import java.net.InetSocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

public class SimpleCanalClientExample {

	public static void main(String args[]) {
		// 创建链接
		CanalConnector connector = CanalConnectors
				.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
		int batchSize = 1000;
		try {
			connector.connect();
			connector.subscribe(".*\\..*");
			connector.rollback();
			while (true) {
				Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
				long batchId = message.getId();
				int size = message.getEntries().size();
				if (batchId == -1 || size == 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				} else {
					// System.out.printf("message[batchId=%s,size=%s] \n",
					// batchId, size);
					printEntry(message.getEntries());
				}

				connector.ack(batchId); // 提交确认
				// connector.rollback(batchId); // 处理失败, 回滚数据
			}

		} finally {
			connector.disconnect();
		}

	}

	private static void printEntry(List<Entry> entrys) {
		for (Entry entry : entrys) {
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
					|| entry.getEntryType() == EntryType.TRANSACTIONEND) {
				continue;
			}

			RowChange rowChage = null;
			try {
				rowChage = RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
				throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
						e);
			}

			EventType eventType = rowChage.getEventType();
			System.out.println("binlog>>>>>>>>" + entry.getHeader().getLogfileName()+":"+entry.getHeader().getLogfileOffset());
			System.out.println("数据库名:>>>>>>>>" + entry.getHeader().getSchemaName());
			System.out.println("表名:>>>>>>>>" + entry.getHeader().getTableName());
			System.out.println("数据库操作类型:>>>>>>>>" + eventType);

			for (RowData rowData : rowChage.getRowDatasList()) {
				if (eventType == EventType.DELETE) {
					printColumn(rowData.getBeforeColumnsList());
				} else if (eventType == EventType.INSERT) {
					printColumn(rowData.getAfterColumnsList());
				} else {
					System.out.println("数据库更新操作之前数据getBeforeColumnsList>>>>>>>>>>>>>>");
					printColumn(rowData.getBeforeColumnsList());
					System.out.println("数据库更新操作之后数据getAfterColumnsList");
					printColumn(rowData.getAfterColumnsList());
				}
			}
		}
	}

	private static void printColumn(List<Column> columns) {
		for (Column column : columns) {
			System.out.println("字段:" + column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
		}
	}
}