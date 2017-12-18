package com.tupengxiong.HBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tupengxiong.HBase.utils.HBaseConvetorUtil;

public class HBaseHelper {
	private static final Logger logger = LoggerFactory.getLogger(HBaseHelper.class);

	private static final String NULL_STRING = new String();
	private static Configuration CONF = new Configuration();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal<Connection> CUP = new ThreadLocal();
	private static String QUORUM;

	public static synchronized Connection getConnection(String quorum) {
		if(quorum == null){
			quorum = "127.0.0.1";
		}
		Connection conn = null;
		if (quorum.equals(QUORUM)) {
			conn = (Connection) CUP.get();
			if ((conn != null) && (!(conn.isClosed())) && (!(conn.isAborted())))
				return conn;
			if ((conn != null) && (conn.isAborted())) {
				close(conn);
			}
		}
		QUORUM = quorum;
		try {
			CONF.set("hbase.zookeeper.quorum", quorum);
			CONF.set("hbase.zookeeper.property.clientPort", "2181");
			CONF.set("hbase.client.pause", "50");
			CONF.set("hbase.client.retries.number", "3");
			CONF.set("hbase.rpc.timeout", "2000");
			CONF.set("hbase.client.operation.timeout", "3000");
			CONF.set("hbase.client.scanner.timeout.period", "10000");
			conn = ConnectionFactory.createConnection(CONF);
			logger.info("Hbase new connection -----------------> new hbase connection");
			CUP.set(conn);
		} catch (IOException e) {
			close(conn);
			logger.error("Hbase connection error please check the configuration", e);
		}
		return conn;
	}

	private static void createTable(String quorum, String table, byte[][] splitKeys, String[] familys) {
		Connection connection = getConnection(quorum);
		Admin admin = null;
		try {
			admin = connection.getAdmin();
			if (admin.tableExists(TableName.valueOf(table))) {
				admin.disableTable(TableName.valueOf(table));
				admin.deleteTable(TableName.valueOf(table));
			}

			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(table));
			for (String family : familys) {
				HColumnDescriptor familyDescriptor = new HColumnDescriptor(family);
				tableDescriptor.addFamily(familyDescriptor);
			}
			if (splitKeys != null) {
				admin.createTable(tableDescriptor, splitKeys);
			}
		} catch (MasterNotRunningException e) {
			logger.error("HBase Master not running ", e);
		} catch (ZooKeeperConnectionException e) {
			logger.error("ZooKeeper Connection error ", e);
		} catch (IOException e) {
			logger.error("Hbase IO Exception ", e);
		} finally {
			close(admin);
		}
	}

	public static void createTable(String quorum, String table, String[] familys) {
		createTable(quorum, table, null, familys);
	}

	public static void addColumnFamily(String quorum, String tableName, String columnFamily) {
		Connection connection = getConnection(quorum);
		Admin admin = null;
		try {
			admin = connection.getAdmin();
			if (!(admin.tableExists(TableName.valueOf(tableName)))) {
				logger.error("table : " + tableName + " is not exist");
			}

			admin.disableTable(TableName.valueOf(tableName));
			HColumnDescriptor hd = new HColumnDescriptor(columnFamily);
			admin.addColumn(TableName.valueOf(tableName), hd);
		} catch (MasterNotRunningException e) {
			logger.error("HBase Master not running ", e);
		} catch (ZooKeeperConnectionException e) {
			logger.error("ZooKeeper Connection error ", e);
		} catch (IOException e) {
			logger.error("Hbase IO Exception ", e);
		} finally {
			close(admin);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void add(String quorum, String tableName, String rowKey, String family, Map<String, Object> data) {
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));

			List puts = new ArrayList();
			for (Map.Entry entry : data.entrySet()) {
				Put put = new Put(Bytes.toBytes(rowKey));
				put.addColumn(family.getBytes(), ((String) entry.getKey()).getBytes(),
						String.valueOf(entry.getValue()).getBytes());
				puts.add(put);
			}
			table.put(puts);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
	}

	@SuppressWarnings("unchecked")
	public static void addList(String quorum, String tableName, Map<KeyValue, Map<String, Object>> data) {
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			@SuppressWarnings("rawtypes")
			List puts = new ArrayList();
			for (Map.Entry<KeyValue, Map<String, Object>> entry : data.entrySet()) {
				for (Map.Entry<String, Object> en : ((Map<String, Object>) entry.getValue()).entrySet()) {
					Put put = new Put(((KeyValue) entry.getKey()).getKey());
					put.addColumn(((KeyValue) entry.getKey()).getKey(), ((String) en.getKey()).getBytes(),String.valueOf(en.getValue()).getBytes());
					puts.add(put);
				}
			}
			table.put(puts);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
	}

	public static void deleteTable(String quorum, String table) {
		Connection connection = getConnection(quorum);
		Admin admin = null;
		try {
			admin = connection.getAdmin();
			if (!(admin.tableExists(TableName.valueOf(table)))) {
				logger.error("table : " + table + " doesn't exist");
			}

			admin.disableTable(TableName.valueOf(table));
			admin.deleteTable(TableName.valueOf(table));
		} catch (MasterNotRunningException e) {
			logger.error("HBase Master not running ", e);
		} catch (ZooKeeperConnectionException e) {
			logger.error("ZooKeeper Connection error ", e);
		} catch (IOException e) {
			logger.error("Hbase IO Exception ", e);
		} finally {
			close(admin);
		}
	}

	public static void delete(String quorum, String tableName, String rowKey) {
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			table.delete(delete);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void delete(String quorum, String tableName, List<String> rowKeys) {
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			List list = new ArrayList();
			table = connection.getTable(TableName.valueOf(tableName));
			for (String key : rowKeys) {
				Delete delete = new Delete(Bytes.toBytes(key));
				list.add(delete);
			}
			table.delete(list);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
	}
	@SuppressWarnings("deprecation")
	public static void deleteColumn(String quorum, String tableName, String rowKey, String family, String qualifier) {
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
			table.delete(delete);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, byte[]>> find(String quorum, String tableName, String family) {
		List data = null;
		Connection connection = getConnection(quorum);
		ResultScanner resultScanner = null;
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			scan.addFamily(Bytes.toBytes(family));
			resultScanner = table.getScanner(Bytes.toBytes(family));
			data = HBaseConvetorUtil.toMap(resultScanner, family);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(resultScanner);
			close(table);
		}
		return data;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, byte[]> find(String quorum, String tableName, String rowKey, String family) {
		Map data = null;
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			data = HBaseConvetorUtil.toMap(result, family);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}

		return data;
	}

	@SuppressWarnings("unchecked")
	public static <T> T find(String quorum, Class<T> clazz, String tableName, String rowKey, String family)
			throws Exception {
		Object data = null;
		Connection connection = getConnection(quorum);
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			data = HBaseConvetorUtil.toObject(clazz, result, family);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(table);
		}
		return (T) data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> getList(String quorum, String tableName, List<Filter> filters)
			throws IOException {
		List data = null;
		Connection connection = getConnection(quorum);
		Table table = null;
		ResultScanner resultScanner = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			FilterList filterList = new FilterList(filters);
			scan.setFilter(filterList);
			resultScanner = table.getScanner(scan);
			data = HBaseConvetorUtil.toMap(resultScanner);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			close(resultScanner);
			close(table);
		}
		return data;
	}

	private static ResultScanner getList(Table table, Filter filter) throws IOException {
		try {
			Scan scan = new Scan();
			if (filter != null) {
				scan.setFilter(filter);
			}
			return table.getScanner(scan);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, byte[]>> find(String quorum, String tableName, Filter filter, String family)
			throws IOException {
		List data = null;
		Connection connection = getConnection(quorum);
		Table table = null;
		ResultScanner resultScanner = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			resultScanner = getList(table, filter);
			data = HBaseConvetorUtil.toMap(resultScanner, family);
		} catch (IOException exc) {
			logger.error("HBaseHelper.find.2", exc);
		} finally {
			close(resultScanner);
			close(table);
		}
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> find(String quorum, Class<T> clazz, String tableName, Filter filter, String family)
			throws Exception {
		Connection connection = getConnection(quorum);
		Table table = null;
		ResultScanner resultScanner = null;
		List data = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			resultScanner = getList(table, filter);
			data = HBaseConvetorUtil.toObject(clazz, resultScanner, family);
		} catch (IOException exc) {
			logger.error("HBaseHelper.find.1", exc);
		} finally {
			close(resultScanner);
			close(table);
		}
		return data;
	}

	public static String getString(Result result, String family, String qualifier) {
		byte[] rs = result.getValue(family.getBytes(), qualifier.getBytes());
		if (rs == null) {
			return NULL_STRING;
		}
		return new String(rs);
	}

	public static int getInt(Result result, String family, String qualifier) {
		byte[] rs = result.getValue(family.getBytes(), qualifier.getBytes());
		if (rs == null) {
			return 0;
		}
		return Bytes.toInt(rs);
	}

	public static long getLong(Result result, String family, String qualifier) {
		byte[] rs = result.getValue(family.getBytes(), qualifier.getBytes());
		if (rs == null) {
			return 0L;
		}
		return Bytes.toLong(rs);
	}

	private static void close(Table table) {
		if (table == null)
			return;
		try {
			table.close();
		} catch (Exception exc) {
			logger.error("HBaseHelper.close", exc);
		}
	}

	private static void close(Admin admin) {
		if (admin == null)
			return;
		try {
			admin.close();
		} catch (Exception exc) {
			logger.error("HBaseHelper.close", exc);
		}
	}

	private static void close(Connection conn) {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (Exception exc) {
			logger.error("HBaseHelper.close", exc);
		}
	}

	private static void close(ResultScanner resultScanner) {
		if (resultScanner != null)
			resultScanner.close();
	}
}