package com.tupengxiong.weixin.bean;

import java.io.Serializable;

/**
 * @author tupx
 */
public class WxText extends WxBase implements Serializable{

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
	
	public WxText(){
		super();
	}
}
