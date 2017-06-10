package com.tupengxiong.weixin.bean;

/**
 * @author tupx
 */
public class WxText {

	private Long id;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 消息的id
	 */
	private String msgId;

	private String toUserName;

	/**
	 * 来自--openid
	 */
	private String fromUserName;

	/**
	 * 消息类型
	 */
	private String msgType;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Override
	public String toString() {
		return "WxText [id=" + id + ", content=" + content + ", msgId=" + msgId + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", msgType=" + msgType + ", delStatus=" + delStatus
				+ ", createTime=" + createTime + ", modifyDate=" + modifyDate + "]";
	}

}
