package com.tupengxiong.elasticsearch.bean.base;

import java.util.Map;

import com.alibaba.otter.canal.protocol.CanalEntry.EventType;

public class ConvertBean {

	/**
	 * 操作类型
	 */
	private EventType eventType;

	/**
	 * 主键名称
	 */
	private String primaryKeyName;

	/**
	 * 主键值
	 */
	private String primaryKey;

	/**
	 * 数据库名称
	 */
	private String dBname;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 键值对
	 */
	private Map<String, Object> paramsMap;

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getdBname() {
		return dBname;
	}

	public void setdBname(String dBname) {
		this.dBname = dBname;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
}
