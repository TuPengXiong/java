package com.lovesher.netty_master.http;
/** 
 * Project Name:netty-rpc 
 * File Name:Configure.java 
 * Package Name:com.tupengxiong.netty.netty_rpc 
 * Date:2017年8月14日下午1:28:07 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/ 

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/** 
 * NettyConfigure <br/> 
 * Function: NettyConfigure. <br/> 
 * Reason:   NettyConfigure. <br/> 
 * Date:     2017年8月14日 下午1:28:07 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class NettyConfigure {

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
	
	private int timeout = 60;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getAggregator() {
		return aggregator;
	}

	public void setAggregator(int aggregator) {
		this.aggregator = aggregator;
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

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
}
  