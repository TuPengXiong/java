package com.tupengxiong.HBase.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseConvetorUtil extends Utils {
	public String getName() {
		return "HBase 辅助转换工具";
	}

	public String getDescription() {
		return "将HBase返回的结果集转换成更易操作的对象";
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toObject(Class<T> clazz, ResultScanner resultScanner, String family) throws Exception {
		List<T> list = new ArrayList<T>();
		for (Result result : resultScanner) {
			Field[] fields = clazz.getDeclaredFields();
			Object t = clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				String fileName = field.getName();
				if (result.containsColumn(Bytes.toBytes(family), Bytes.toBytes(fileName))) {
					if (result.getValue(Bytes.toBytes(family), Bytes.toBytes(fileName)).length == 0) {
						continue;
					}
					String value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(fileName)));
					field.set(t, value);
				}
			}
			list.add((T) t);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(Class<T> clazz, Result result, String family) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		Object t = clazz.newInstance();
		for (Field field : fields) {
			field.setAccessible(true);
			String fileName = field.getName();
			if (result.containsColumn(Bytes.toBytes(family), Bytes.toBytes(fileName))) {
				if (result.getValue(Bytes.toBytes(family), Bytes.toBytes(fileName)).length == 0) {
					continue;
				}
				String value = Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(fileName)));
				field.set(t, value);
			}
		}

		return (T) t;
	}

	public static List<Map<String, byte[]>> toMap(ResultScanner resultScanner, String family) {
		List<Map<String, byte[]>> list = new ArrayList<Map<String, byte[]>>();
		for (Result result : resultScanner) {
			Map<String, byte[]> map = new HashMap<String, byte[]>();
			for (@SuppressWarnings("rawtypes") Map.Entry rs : result.getFamilyMap(family.getBytes()).entrySet()) {
				map.put(new String((byte[]) rs.getKey()), (byte[]) rs.getValue());
			}

			list.add(map);
		}
		return list;
	}

	public static Map<String, byte[]> toMap(Result result, String family) {
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		for (@SuppressWarnings("rawtypes") Map.Entry rs : result.getFamilyMap(family.getBytes()).entrySet()) {
			map.put(new String((byte[]) rs.getKey()), (byte[]) rs.getValue());
		}
		return map;
	}

	@SuppressWarnings("deprecation")
	public static List<Map<String, Object>> toMap(ResultScanner resultScanner) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Result result : resultScanner) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (KeyValue keyValue : result.raw()) {
				map.put(new String(keyValue.getQualifier()), new String(keyValue.getValue()));
			}
			list.add(map);
		}
		return list;
	}

	public static String getRowKey() {
		int j = (int) (Math.random() * 9999.0D);
		++j;
		String strHao = String.valueOf(j);
		while (strHao.length() < 4) {
			strHao = "0" + strHao;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(System.currentTimeMillis()).append(strHao);
		return sb.toString();
	}
}