package com.tupengxiong.jvm.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中
 * @ClassName:  BufferFactory   
 * @Description:所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月12日 上午10:23:25   
 * @version V1.0
 */
public class BufferFactory {

	
	Buffer getByteBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	
	Buffer getCharBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	
	Buffer getDoubleBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	Buffer getFloatBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	Buffer getIntBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	Buffer getLongBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
	Buffer getShortBuffer(){
		return ByteBuffer.allocate(1000);
		
	}
}
