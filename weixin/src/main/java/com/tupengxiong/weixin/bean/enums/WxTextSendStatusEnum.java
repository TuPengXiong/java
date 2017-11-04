package com.tupengxiong.weixin.bean.enums;

public enum WxTextSendStatusEnum {

	INIT(0,"微信消息获取"),
	TASK(1,"消息已经加载到任务中"),
	SUCCESS(2,"消息转发成功"),
	FAIL(3,"消息转发失败");
	private final int status;
	private final String desp;
	private WxTextSendStatusEnum(int status,String desp){
		this.status = status;
		this.desp = desp;
	}
	
	public int getStatus() {
		return status;
	}
	public String getDesp() {
		return desp;
	}
}
