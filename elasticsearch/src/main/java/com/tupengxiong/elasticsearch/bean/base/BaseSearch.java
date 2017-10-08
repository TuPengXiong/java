package com.tupengxiong.elasticsearch.bean.base;

import java.util.Date;

/**
 * 基础搜索条件
 * 
 * @author tupx
 */
public abstract class BaseSearch {

	/** 名称 **/
	private String name;

	/** 删除状态 **/
	private Boolean delStatus;

	/** 开始添加时间 **/
	private Date addStartTime;

	/** 结束添加时间 **/
	private Date addEndTime;

	/** 开始更新时间 **/
	private Date modifyStartTime;

	/** 结束更新时间 **/
	private Date modifyEndTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}

	public Date getAddStartTime() {
		return addStartTime;
	}

	public void setAddStartTime(Date addStartTime) {
		this.addStartTime = addStartTime;
	}

	public Date getAddEndTime() {
		return addEndTime;
	}

	public void setAddEndTime(Date addEndTime) {
		this.addEndTime = addEndTime;
	}

	public Date getModifyStartTime() {
		return modifyStartTime;
	}

	public void setModifyStartTime(Date modifyStartTime) {
		this.modifyStartTime = modifyStartTime;
	}

	public Date getModifyEndTime() {
		return modifyEndTime;
	}

	public void setModifyEndTime(Date modifyEndTime) {
		this.modifyEndTime = modifyEndTime;
	}

}
