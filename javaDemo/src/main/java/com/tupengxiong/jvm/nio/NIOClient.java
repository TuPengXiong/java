package com.tupengxiong.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class NIOClient {

	private int BLOCK = 4096;

	private ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK);

	private ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK);

	private Selector selector;

	private CountDownLatch latch = new CountDownLatch(1);

	private SocketChannel socketChannel;

	public NIOClient(String host, int port) throws IOException {
		socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);

		selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress(host, port));
	}

	/**
	 * 连接服务端，并进行请求
	 * 
	 * @throws IOException
	 */
	public void connect() throws IOException {
		while (true) {
			selector.select();
			handleKey(selector.selectedKeys());
			selector.selectedKeys().clear();
		}
	}

	/**
	 * 对select到的key进行处理
	 * 
	 * @param selectionKeys
	 * @throws IOException
	 */
	public void handleKey(Set<SelectionKey> selectionKeys) throws IOException {
		Iterator<SelectionKey> itr = selectionKeys.iterator();
		while (itr.hasNext()) {
			SelectionKey selectionKey = itr.next();
			SocketChannel client = (SocketChannel) selectionKey.channel();

			if (selectionKey.isConnectable()) {
				System.out.println("client connect...");
				latch.countDown();
				if (client.isConnectionPending()) {
					client.finishConnect();
					System.out.println("client connected...!");
					latch.countDown();
				}
			} else if (selectionKey.isReadable()) {
				read(client);
			} else if (selectionKey.isWritable()) {
				write(client, new byte[0]);
			}
		}
		selectionKeys.clear();
	}

	public void write(SocketChannel client, byte[] content) throws IOException {
		if (content.length != 0) {
			sendBuffer.clear();
			sendBuffer.put(content);
			sendBuffer.flip();
			client.write(sendBuffer);
			System.out.println("[send message to server]:" + new String(content));
		}
		client.register(selector, SelectionKey.OP_READ); // 注册读操作
	}

	public void read(SocketChannel client) throws IOException {
		receiveBuffer.clear();
		int count = client.read(receiveBuffer);
		if (count > 0) {
			String receiveText = new String(receiveBuffer.array(), 0, count);
			System.out.println("[server message]:" + receiveText);
			client.register(selector, SelectionKey.OP_WRITE);// 注册写操作
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final NIOClient client = new NIOClient("127.0.0.1", 8888);

		new Thread(new Runnable() {
			public void run() {
				try {
					client.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		client.latch.await();

		client.write(client.socketChannel, ("message from client " + UUID.randomUUID()).getBytes());
		client.write(client.socketChannel, ("message from client " + UUID.randomUUID()).getBytes());
		client.write(client.socketChannel, ("message from client " + UUID.randomUUID()).getBytes());
		client.write(client.socketChannel, ("message from client " + UUID.randomUUID()).getBytes());
	}
}