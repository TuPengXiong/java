package com.tupengxiong.jvm.nio.support;

public interface Channel {

	void onConneted();
	
	
	void onException(Exception e);
}
