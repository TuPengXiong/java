package com.tupengxiong.netty.publisher;

import java.net.InetSocketAddress;

import com.tupengxiong.netty.consumer.HttpServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class HttpServer {
	@SuppressWarnings("unused")
	private static final int DEFAULT_THREADS = Math.max(1,
			SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 4));
	private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private InternalLogger logger = InternalLoggerFactory.getInstance("HttpServer");
	private ChannelHandler channelHandler;
	/**
	 * 服务端口号
	 */
	private int port = 8000;
	/**
	 * 
	 */
	private int aggregator = 262144;
	/**
	 * 服务启动工具类
	 */
	private ServerBootstrap bootstrap;
	/**
	 * bossGroup用于接受请求的线程组
	 */
	private NioEventLoopGroup bossGroup;
	/**
	 * bossGroup收到请求之后叫workerGroup去处理
	 */
	private NioEventLoopGroup workGroup;
	/**
	 * 初始化服务端可连接队列数量
	 */
	private int backlog = 10240;
	/**
	 * 套接字 接收缓冲区大小
	 */
	private int rcvbuf = 262144;
	/**
	 * 套接字 发送缓冲区大小
	 */

	private int sndbuf = 262144;
	/**
	 * 该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的
	 * 连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
	 */
	private boolean keepalive = true;

	/**
	 * 当用户调用close（）方法的时候，函数返回，在可能的情况下，尽量发送数据，不一定保证
	 * 会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送
	 */
	private int linger = 0;

	public ChannelGroup getChannels() {
		return channels;
	}

	public void setChannels(ChannelGroup channels) {
		this.channels = channels;
	}

	public ServerBootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(ServerBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public NioEventLoopGroup getBossGroup() {
		return bossGroup;
	}

	public void setBossGroup(NioEventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}

	public NioEventLoopGroup getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(NioEventLoopGroup workGroup) {
		this.workGroup = workGroup;
	}

	public void startHttpserver() throws Exception {

		bossGroup = new NioEventLoopGroup();
		workGroup = new NioEventLoopGroup();
		// bossGroup收到请求之后叫workerGroup去处理
		bootstrap = new ServerBootstrap();
		// ChannelPipeline用于链式管理内部的channel处理类.
		this.bootstrap.group(bossGroup, workGroup);
		this.bootstrap.channel(NioServerSocketChannel.class);
		this.bootstrap.childHandler(channelHandler);
		this.bootstrap.option(ChannelOption.SO_BACKLOG, this.backlog);
		this.bootstrap.option(ChannelOption.SO_RCVBUF, Integer.valueOf(this.rcvbuf));
		this.bootstrap.option(ChannelOption.SO_SNDBUF, Integer.valueOf(this.sndbuf));
		this.bootstrap.option(ChannelOption.SO_LINGER, this.linger);
		this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		this.bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		this.bootstrap.childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(this.keepalive));
		this.bootstrap.childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
		// 线程同步阻塞等待服务器绑定到指定端口,
		final ChannelFuture f = this.bootstrap.bind(new InetSocketAddress(port)).sync(); // (7)
		// 成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
		new Thread(new Runnable() {

			public void run() {
				try {
					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					
				} finally {
					workGroup.shutdownGracefully();
					bossGroup.shutdownGracefully();
				}
			}
		}).start();

		logger.info("server start at port: " + port);
	}

	public static void main(String[] args) throws Exception {
		HttpServer server = new HttpServer();
		ChannelHandler channelHandler = new HttpServerInitializer(server);
		server.setChannelHandler(channelHandler);
		server.startHttpserver();
	}

	public boolean remove(Channel channel) {
		return this.channels.remove(channel);
	}

	public int getAggregator() {
		return aggregator;
	}

	public void setAggregator(int aggregator) {
		this.aggregator = aggregator;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getRcvbuf() {
		return rcvbuf;
	}

	public void setRcvbuf(int rcvbuf) {
		this.rcvbuf = rcvbuf;
	}

	public int getSndbuf() {
		return sndbuf;
	}

	public void setSndbuf(int sndbuf) {
		this.sndbuf = sndbuf;
	}

	public boolean isKeepalive() {
		return keepalive;
	}

	public void setKeepalive(boolean keepalive) {
		this.keepalive = keepalive;
	}

	public int getLinger() {
		return linger;
	}

	public void setLinger(int linger) {
		this.linger = linger;
	}

	public ChannelHandler getChannelHandler() {
		return channelHandler;
	}

	public void setChannelHandler(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}

}