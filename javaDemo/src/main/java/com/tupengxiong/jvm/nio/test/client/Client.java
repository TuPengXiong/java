package com.tupengxiong.jvm.nio.test.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.tupengxiong.jvm.nio.channel.ChannelFactory;
import com.tupengxiong.jvm.nio.selector.SelectionKeyFactory;
import com.tupengxiong.jvm.nio.selector.SelectorFactory;

public class Client {

	public static void main(String[] args) {
		try {
			run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void run() throws IOException {
		Selector selector = SelectorFactory.getSelector();
		SocketChannel socketChannel = ChannelFactory.getSocketChannel();
		socketChannel.configureBlocking(false);
		if (socketChannel.connect(new InetSocketAddress(6399))) {
			SelectionKeyFactory.channelRegisterToSelector(socketChannel, selector, SelectionKey.OP_READ,UUID.randomUUID());
		} else {
			SelectionKeyFactory.channelRegisterToSelector(socketChannel, selector, SelectionKey.OP_CONNECT,UUID.randomUUID());
		}
		while (true) {
			if (selector.select(1000) > 0) {
				selectKeys(selector);
			}
		}
	}

	private static void doWrite(SocketChannel sc, String data) throws IOException {
		byte[] req = data.getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
		byteBuffer.put(req);
		byteBuffer.flip();
		sc.write(byteBuffer);
		if (!byteBuffer.hasRemaining()) {
			System.out.println("Send 2 client successed");
		}
	}

	private static void selectKeys(Selector selector) throws IOException {

		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		while (keyIterator.hasNext()) {
			SelectionKey key = keyIterator.next();
			keyIterator.remove();
			SocketChannel channel = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				// a connection was accepted by a ServerSocketChannel.
				System.out.println("a connection was accepted by a ServerSocketChannel");
				// 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
				// 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
				if (key.isConnectable()) {
					// 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
					// 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
					if (channel.finishConnect()) {
						// 处理完后必须吧OP_CONNECT关注去掉，改为关注OP_READ
						key.interestOps(SelectionKey.OP_READ);
						channel.register(selector, SelectionKey.OP_READ);
						doWrite(channel, "66666666");
					} else {
						// 链接失败，进程推出
						System.exit(1);
					}
				} else {
					// 链接失败，进程推出
					System.exit(1);
				}
			} else if (key.isReadable()) {
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int readBytes = channel.read(buffer);
				String content = "";
				if (readBytes > 0) {
					buffer.flip();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					content += new String(bytes);
				} else if (readBytes < 0) {
					// 对端链路关闭
					key.channel();
					channel.close();
				}
				System.out.println(content);
				key.interestOps(SelectionKey.OP_READ);
			} 

		}
	}
}
