package com.tupengxiong.netty.consumer;

import com.tupengxiong.netty.handler.HttpClientInboundHandler;
import com.tupengxiong.netty.publisher.HttpServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	private HttpServer server;

	public HttpServerInitializer(HttpServer server) {
		this.server = server;
	}

	public boolean remove(Channel channel) {
		return this.server.remove(channel);
	}

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(this.server.getAggregator()));
		pipeline.addLast("encoder", new HttpResponseEncoder());

		pipeline.addLast("handler", new HttpClientInboundHandler());
	}

	public HttpServer getServer() {
		return server;
	}

	public void setServer(HttpServer server) {
		this.server = server;
	}
	
}