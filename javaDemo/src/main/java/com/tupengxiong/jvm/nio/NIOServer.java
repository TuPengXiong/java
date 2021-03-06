package com.tupengxiong.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	// 读写缓冲区大小
	private int BLOCK = 4096;

	// 发送缓冲
	private ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK);

	// 接收缓冲
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK);

	private Selector selector;
	
	private int flag = 0;

	public NIOServer(int port) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);

		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));

		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server start --- " + port);
	}

	/**
	 * 监听
	 * 
	 * @throws IOException
	 */
	private void listen() throws IOException {
		while (true) {
			selector.select();

			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> itr = selectionKeys.iterator();
			while (itr.hasNext()) {
				SelectionKey selectionKey = itr.next();
				itr.remove();
				handleKey(selectionKey);// 处理 selectionKey
			}
		}
	}

	/**
	 * 处理 selectionKey,读写
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleKey(SelectionKey selectionKey) throws IOException {
		SocketChannel client = null;
		if (selectionKey.isAcceptable()) {
			ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

			client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		} else if (selectionKey.isReadable()) {
			read((SocketChannel) selectionKey.channel());
		} else if (selectionKey.isWritable()) {
			if(flag<=3){
				flag++;
				write((SocketChannel) selectionKey.channel(), "你好 客户端".getBytes());
			}else{
				write((SocketChannel) selectionKey.channel(), new byte[0]);
			}
		}
	}

	public void write(SocketChannel client, byte[] content) throws IOException {

		if (content.length != 0) {
			sendBuffer.clear();
			sendBuffer.put(content);
			sendBuffer.flip();
			client.write(sendBuffer);
			String ip = client.socket().getInetAddress().getHostAddress();
			int port = client.socket().getPort();
			System.out.println("[send message to client]:" + "[" + ip + ":" + port + "]" + new String(content));
		}
		client.register(selector, SelectionKey.OP_READ); // 注册读操作
	}

	public void read(SocketChannel client) throws IOException {
		receiveBuffer.clear();
		int count = client.read(receiveBuffer);
		if (count > 0) {
			String receiveText = new String(receiveBuffer.array(), 0, count);
			String ip = client.socket().getInetAddress().getHostAddress();
			int port = client.socket().getPort();
			System.out.println("[client message]:" + "[" + ip + ":" + port + "]" + receiveText);
			client.register(selector, SelectionKey.OP_WRITE);// 注册写操作
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 8888;
		NIOServer server = new NIOServer(port);
		server.listen();
	}
}