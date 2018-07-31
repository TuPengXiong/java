package com.tupengxiong.jvm.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;

/**
 * 
 * @ClassName:  BufferFactory   
 * @Description:BufferFactory
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月12日 下午12:37:36   
 * @version V1.0
 */
public class BufferFactory {

	public static ByteBuffer getByteBuffer(int capacity){
		return ByteBuffer.allocate(capacity);
	}
	
	public static CharBuffer getCharBuffer(int capacity){
		return CharBuffer.allocate(capacity);
	}
	
	public static FloatBuffer getDoubleBuffer(int capacity){
		return FloatBuffer.allocate(capacity);
	}
	
	public static FloatBuffer getFloatBuffer(int capacity){
		return FloatBuffer.allocate(capacity);
	}
	
	public static IntBuffer getIntBuffer(int capacity){
		return IntBuffer.allocate(capacity);
	}
	
	public static LongBuffer getLongBuffer(int capacity){
		return LongBuffer.allocate(capacity);
	}
	
	public static ShortBuffer getShortBuffer(int capacity){
		return ShortBuffer.allocate(capacity);
	}
	
	public static ByteBuffer getMappedByteBuffer(int capacity){
		return MappedByteBuffer.allocate(capacity);
	}
}
