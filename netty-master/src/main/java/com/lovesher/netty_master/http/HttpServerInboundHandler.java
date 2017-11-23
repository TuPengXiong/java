package com.lovesher.netty_master.http;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		super.channelActive(ctx);
		// 与服务端建立连接后
		System.out.println("客户端链接成功>>>>>>>>>>>>>" + ctx.channel().remoteAddress().toString());
	}

	public static AtomicInteger httpReceiveTotal = new AtomicInteger();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest request = (FullHttpRequest) msg;
			HttpMethod method = request.getMethod();
			HttpVersion httpVersion = request.getProtocolVersion();
			System.out.println("httpVersion>>>>>>>>>>>>>>" + httpVersion.text());
			System.out.println("method>>>>>>>>>>>>>>" + method.name());
			System.out.println("Uri>>>>>>>>>>>>>>" + request.getUri());
			HttpHeaders headers = request.headers();
			for(Map.Entry<String, String> map: headers.entries()){
				System.out.println("headers>>>>>>>>>>"+map.getKey() +">>>>>>>>"+ map.getValue());
			}
			
			ByteBuf content = request.content();
			final StringBuilder contentSb = new StringBuilder();
			contentSb.append(content.toString(CharsetUtil.UTF_8));
			System.out.println("content>>>>>>>>>>>>>>" + contentSb.toString());
			QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> uriAttributes = decoderQuery.parameters();
			for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
				System.out.println(attr.getKey() + ">>>>>>>>>>>>>>>>>" + attr.getValue());
			}
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			// Thread.sleep(10000L);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
			int i = httpReceiveTotal.incrementAndGet();
			// 转发
			String responseMessage = "false============>" + i + ">>>>" + contentSb.toString();
			ByteBuf buffer = Unpooled.copiedBuffer(responseMessage, CharsetUtil.UTF_8);
			response.content().writeBytes(buffer);
			// 释放缓冲区
			buffer.release();
			// 返回数据
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

		}
		
		/*if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			HttpMethod method = request.getMethod();
			HttpVersion httpVersion = request.getProtocolVersion();
			System.out.println("httpVersion>>>>>>>>>>>>>>" + httpVersion.text());
			System.out.println("method>>>>>>>>>>>>>>" + method.name());
			System.out.println("Uri>>>>>>>>>>>>>>" + request.getUri());
			final StringBuilder contentSb = new StringBuilder();
			System.out.println("content>>>>>>>>>>>>>>" + contentSb.toString());
			QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> uriAttributes = decoderQuery.parameters();
			for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
				System.out.println(attr.getKey() + ">>>>>>>>>>>>>>>>>" + attr.getValue());
			}
		}
		if (msg instanceof LastHttpContent) {
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			final StringBuilder buf = new StringBuilder();
			buf.append(content.toString(CharsetUtil.UTF_8));

			System.out.println("body==============>" + buf.toString());
			int i = httpReceiveTotal.incrementAndGet();
			// 转发
			String responseMessage = "false============>" + i + ">>>>" + buf.toString();
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			// Thread.sleep(10000L);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
			ByteBuf buffer = Unpooled.copiedBuffer(responseMessage, CharsetUtil.UTF_8);
			response.content().writeBytes(buffer);
			// 释放缓冲区
			buffer.release();
			// 返回数据
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}*/
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.channel().close();
	}
}