package com.tupengxiong.weixin.bean;

public class WxBase extends DO {

	private Long id;

	private String toUserName;

	private String fromUserName;

	private String msgType;

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
	private Long modifyDate;

	public Boolean getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}

	public Long getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Long modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
