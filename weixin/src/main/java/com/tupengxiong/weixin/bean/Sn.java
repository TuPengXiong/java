package com.tupengxiong.weixin.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: Sn.java
 * @version V1.0
 * 
 */
public class Sn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6183725738363582564L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 数据状态：0，正常 1删除
	 */
	private Boolean delStatus;

	/**
	 * 记录创建时间
	 */
	private Date createTime;

	/**
	 * 记录修改时间
	 */
	private Date modifyTime;
	
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

	public Boolean getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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
