package com.tupengxiong.weixin.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: WxText <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年6月14日 下午5:51:58 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 */
public class WxText extends WxBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649128178861636139L;

	private Long id;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 消息的id
	 */
	private String msgId;

	private String name;

	private Integer sendStatus = 0;

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public WxText() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
