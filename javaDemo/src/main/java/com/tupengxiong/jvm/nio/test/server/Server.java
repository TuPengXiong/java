package com.tupengxiong.jvm.nio.test.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.tupengxiong.jvm.nio.channel.ChannelFactory;
import com.tupengxiong.jvm.nio.selector.SelectionKeyFactory;
import com.tupengxiong.jvm.nio.selector.SelectorFactory;

public class Server {
	private static Selector selector = null;

	static {
		try {
			selector = SelectorFactory.getSelector();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void start() throws IOException {
		ServerSocketChannel serverSocketChannel = ChannelFactory.getServerSocketChannel();
		/**
		 * 与Selector一起使用时，Channel必须处于非阻塞模式下
		 * 不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式
		 */
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(6399));
		SelectionKeyFactory.channelRegisterToSelector(serverSocketChannel, selector, SelectionKey.OP_ACCEPT ,null);
		while (true) {
			// 非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				// do something with socketChannel...
				/**
				 * 与Selector一起使用时，Channel必须处于非阻塞模式下
				 * 不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式
				 */
				System.out.println("有新客户登录了...IP:" + socketChannel.socket().getInetAddress().getHostAddress() + " port:"
						+ socketChannel.socket().getPort());


				int readyChannels = selector.select();
				System.out.println("readyChannels:" + readyChannels);
				selectKeys(selector);
			}
		}
	}

	private static void selectKeys(Selector selector) throws IOException {

		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		while (keyIterator.hasNext()) {
			SelectionKey key = keyIterator.next();
			keyIterator.remove();
			if (key.isAcceptable()) {
				// a connection was accepted by a ServerSocketChannel.
				System.out.println("a connection was accepted by a ServerSocketChannel");
				
				ServerSocketChannel ssc  = (ServerSocketChannel) key.channel();
                // 得到与客户端的套接字通道
                SocketChannel channel = ssc.accept();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
                //将key对应Channel设置为准备接受其他请求
                key.interestOps(SelectionKey.OP_ACCEPT);
			} else if (key.isConnectable()) {
				// a connection was established with a remote server.
				System.out.println("a connection was established with a remote server");
			} else if (key.isReadable()) {
				// a channel is ready for reading
				System.out.println("a channel is ready for readings");
				SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                String content = "";
                
                try {
                    int readBytes = channel.read(byteBuffer);
                    if (readBytes > 0) {
                        byteBuffer.flip(); //为write()准备
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bytes);
                        content+=new String(bytes);
                        System.out.println(content);
                        //回应客户端
                        doWrite(channel);
                    }
                    // 写完就把状态关注去掉，否则会一直触发写事件(改变自身关注事件)
                    key.interestOps(SelectionKey.OP_READ);
                } catch (IOException i) {
                    //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
                    //所以从Selector中取消该SelectionKey的注册
                    key.cancel();
                    if (key.channel() != null) {
                        key.channel().close();
                    }
                }
			} else if (key.isWritable()) {
				// a channel is ready for writing
				System.out.println("a channel is ready for writing");
			}
			
		}
	}

	private static void doWrite(SocketChannel sc) throws IOException {
		byte[] req = "服务器已接受".getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
		byteBuffer.put(req);
		byteBuffer.flip();
		sc.write(byteBuffer);
		if (!byteBuffer.hasRemaining()) {
			System.out.println("Send 2 Service successed");
		}
	}
}
