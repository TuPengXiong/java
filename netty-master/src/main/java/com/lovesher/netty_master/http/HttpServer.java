package com.lovesher.netty_master.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpServer {
	
	private NettyConfigure nettyConfigure = new NettyConfigure();

	public void start(int port) throws Exception {
		final EventLoopGroup bossGroup = new NioEventLoopGroup();
		final EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		try {
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// HTTP 请求消息解码器
							ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
							// HttpObjectAggregator解码器，将多个消息转换成单一的FullHttpRequest或者FullHTtpResponse，原因是HTTP解码器在每个HTTP消息中会生成多个消息对象（HttpRequst、HttpResponse、Httpontent/LastHttpContent）。
							ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(nettyConfigure.getAggregator()));
							// HTTP响应编码器
							ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
							// Chunked
							// handler它的作用是支持异步发送大的码流但不占用过多的内存，防止发生java内存溢出错误。
							ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
							ch.pipeline().addLast(new HttpServerInboundHandler());
						}
					});
			bootstrap.option(ChannelOption.SO_BACKLOG, nettyConfigure.getBacklog());
			bootstrap.option(ChannelOption.SO_RCVBUF, Integer.valueOf(nettyConfigure.getRcvbuf()));
			bootstrap.option(ChannelOption.SO_SNDBUF, Integer.valueOf(nettyConfigure.getSndbuf()));
			bootstrap.option(ChannelOption.SO_LINGER, nettyConfigure.getLinger());
			bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			bootstrap.option(ChannelOption.SO_TIMEOUT, Integer.valueOf(nettyConfigure.getTimeout()));
			bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(nettyConfigure.isKeepalive()));
			bootstrap.childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
			
			ChannelFuture channelFuture = bootstrap.bind(nettyConfigure.getPort()).sync();

			channelFuture.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws Exception {
		HttpServer server = new HttpServer();
		System.out.println("Http Server listening on 8000 ...");
		server.start(8000);
	}
}