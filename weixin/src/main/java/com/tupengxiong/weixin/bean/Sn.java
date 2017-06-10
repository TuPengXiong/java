package com.tupengxiong.weixin.bean;


/**
 * @Title: Sn.java
 * @version V1.0
 * 
 */
public class Sn extends DO {
	
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 序列号类型
	 */
	private String snType;
	
	/** 
	 * 末值
	 */
	private Long lastValue;
	
	/**
	 * 版本号
	 */
	private int version; 

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public String getSnType() {
		return snType;
	}

	/**
	 * 设置类型
	 * 
	 * @param snType
	 *            类型
	 */
	public void setType(String snType) {
		this.snType = snType;
	}

	/**
	 * 获取末值
	 * 
	 * @return 末值
	 */
	public Long getLastValue() {
		return lastValue;
	}

	/**
	 * 设置末值
	 * 
	 * @param lastValue
	 *            末值
	 */
	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
