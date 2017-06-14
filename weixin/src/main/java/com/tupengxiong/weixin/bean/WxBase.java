package com.tupengxiong.weixin.bean;

import java.util.Date;
/**
 * 
 * ClassName: WxBase <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年6月14日 下午5:52:09 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public abstract class WxBase {

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
	 * 公众号
	 */
	private String toUserName;

	/**
	 * 用户openid
	 */
	private String fromUserName;

	/**
	 * 消息类型
	 */
	private String msgType;

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

	public Boolean getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Boolean delStatus) {
		this.delStatus = delStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
