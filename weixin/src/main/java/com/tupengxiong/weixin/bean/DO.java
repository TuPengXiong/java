package com.tupengxiong.weixin.bean;


/**
 * 定义实体基类表对应的DO
 * 
 * @author 
 * 
 */
public abstract class DO {
	
	public static final int STATUS_NORMAL = 1;

	/**
	 * 数据状态：0，正常 1删除
	 */
	private Boolean delStatus;

	/**
	 * 记录创建时间
	 */
	private Long createTime;

	/**
	 * 记录修改时间
	 */
	private Long modifyTime;

	public DO() {
		super();
	}

	public Boolean getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	
}
