package com.tupengxiong.jvm.nio.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {
	
	public static void main(String[] args) throws IOException {
		channelBuffer();
	}

	private static void channelBuffer() throws IOException{
		String name = NioTest.class.getClassLoader().getResource("README.md").getPath();
		RandomAccessFile aFile = new RandomAccessFile(name, "rw");
		/**
		 * 获取文件channel FileChannelImpl
		 */
		FileChannel inChannel = aFile.getChannel();
		/**
		 * 缓冲字节数   HeapByteBuffer position=0 limit=48  capacity=48
		 */
		ByteBuffer buf = ByteBuffer.allocate(48);
		System.out.println("position:" + buf.position());
		System.out.println("limit:" + buf.limit());
		System.out.println("capacity:" + buf.capacity());
		System.out.println("-------------start write and read -----------");
		/**
		 * 从channel读取字节 写入 buf FileChannelImpl 返回实际写入的字节数 大小 position=48 limit=48  capacity=48 
		 */
		int bytesRead = inChannel.read(buf);
		System.out.println("position:" + buf.position());
		System.out.println("limit:" + buf.limit());
		System.out.println("capacity:" + buf.capacity());
		System.out.println("-------------start write once end.  start to  read -----------");
		while (bytesRead != -1) {
			/**
			 * flip()方法将Buffer从写模式切换到读模式 在读模式下，可以读取之前写入到buffer的所有数据
			 * limit = position;写入的position
			 * position = 0;
			 * mark = -1
			 * 从 position =0 读取到 position=limit(48)
			 */
			buf.flip();
			
			System.out.println("position:" + buf.position());
			System.out.println("limit:" + buf.limit());
			System.out.println("capacity:" + buf.capacity());
			/**
			 * 判断缓冲区是否含有数据
			 * position < limit
			 */
			if(buf.hasRemaining()){
				/**
				 * 读取的内容
				 */
				System.out.println(new String(buf.array(),"utf-8"));
			}
			/**
			 * 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
			 * 有两种方式能清空缓冲区：调用clear()或compact()方法。
			 * clear()方法会清空整个缓冲区。 position = 0;limit = capacity;mark = -1;
			 * compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
			 * 
			 * 可以写入 position=0 到 limit=capacity 个字节数
			 */
			buf.clear();
			
			/**
			 * 从channel读取字节 写入 buf FileChannelImpl 返回实际写入的字节数 大小 position=0 limit=capacity  capacity=48 
			 */
			bytesRead = inChannel.read(buf);
		}
		
		//关闭文件channel流
		aFile.close();
	}
	
}
