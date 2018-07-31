package com.tupengxiong.jvm.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;

/**
 * 所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中
 * @ClassName:  ChannelFactory   
 * @Description:所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月12日 上午10:22:58   
 * @version V1.0
 */
public class ChannelFactory {

	public static FileChannel getFileChannel(Path path, OpenOption... options) throws IOException{
		return FileChannel.open(path, options);
	}
	
	public static FileChannel getFileChannel(Path path,Set<? extends OpenOption> options,FileAttribute<?>... attrs) throws IOException{
		return FileChannel.open(path, options, attrs);
	}
	
	public static FileChannel getFileChannel(RandomAccessFile randomAccessFile){
		return randomAccessFile.getChannel();
	}
	
	@SuppressWarnings("resource")
	public static FileChannel getFileChannel(String name, String mode) throws FileNotFoundException{
		return new RandomAccessFile(name,mode).getChannel();
	}
	
	public static DatagramChannel getDatagramChannel() throws IOException{
		return DatagramChannel.open();
	}
	
	public static DatagramChannel getDatagramChannel(ProtocolFamily family) throws IOException{
		return DatagramChannel.open(family);
	}
	
	public static SocketChannel getSocketChannel() throws IOException{
		return SocketChannel.open();
	}
	
	public static SocketChannel getSocketChannel(SocketAddress remote) throws IOException{
		return SocketChannel.open(remote);
	}

	public static ServerSocketChannel getServerSocketChannel() throws IOException{
		return ServerSocketChannel.open();
	}
	
	public static Pipe getPipe() throws IOException{
		return Pipe.open();
	}
}
