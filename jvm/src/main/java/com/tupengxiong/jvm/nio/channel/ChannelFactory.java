package com.tupengxiong.jvm.nio.channel;

import java.nio.channels.Channel;

/**
 * 所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中
 * @ClassName:  ChannelFactory   
 * @Description:所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月12日 上午10:22:58   
 * @version V1.0
 */
public class ChannelFactory {

	
	Channel getFileChannel(){
		return null;
	}
	
	Channel getDatagramChannel(){
		return null;
		
	}
	
	Channel getSocketChannel(){
		return null;
		
	}
	
	Channel getServerSocketChannel(){
		return null;
		
	}
}
