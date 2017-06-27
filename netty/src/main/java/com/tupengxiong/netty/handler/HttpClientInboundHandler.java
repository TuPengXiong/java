package com.tupengxiong.netty.handler;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tupengxiong.netty.consumer.HttpServerInitializer;
import com.tupengxiong.netty.publisher.HttpServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class HttpClientInboundHandler extends SimpleChannelInboundHandler<Object> {
	private HttpServerInitializer initializer;
	private AttributeKey<Map<String, Object>> paramKey = AttributeKey.valueOf("param");
	private AttributeKey<HttpRequest> requestKey = AttributeKey.valueOf("request");

	public HttpClientInboundHandler(HttpServerInitializer initializer) {
		this.initializer = initializer;
	}

	
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		this.initializer.remove(ctx.channel());
		super.channelInactive(ctx);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}

	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		super.channelWritabilityChanged(ctx);
	}

	public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg.getClass());

		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			/*Map param = (Map) ctx.channel().attr(this.paramKey).get();
			if (param == null) {
				param = new HashMap();
				ctx.channel().attr(this.paramKey).set(param);
			}

			QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> uriAttributes = decoderQuery.parameters();
			for (Map.Entry attr : uriAttributes.entrySet()) {
				param.put(attr.getKey(),
						(((List) attr.getValue()).size() > 0) ? (String) ((List) attr.getValue()).get(0) : null);
			}
			param.put("___ctx___", decoderQuery.path().substring(1));*/
			Attribute requestVal = ctx.channel().attr(this.requestKey);
			requestVal.set(request);
		}

		if (!(msg instanceof HttpContent))
			return;
		HttpContent chunk = (HttpContent) msg;
		if (chunk instanceof LastHttpContent) {
			byte[] buf = new byte[chunk.content().readableBytes()];
			chunk.content().readBytes(buf);
			String json = new String(buf, "UTF-8");
			HttpRequest request = ctx.channel().attr(this.requestKey).get();
			writeResponse(request.getUri(), ctx.channel(), request, RuntimeException.class.getName(), "err", "error");
			//clear(ctx);
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		StringBuilder sb = new StringBuilder("clientreset addr=");
		sb.append(ctx.channel().remoteAddress().toString());
		ctx.channel().close();
		this.initializer.getServer().logger(sb.toString());
	}

	private void writeResponse(String actn, Channel channel, HttpRequest request, String claz, String errs,
			String body) {
		ByteBuf buf = Unpooled.copiedBuffer(body, Charset.forName("UTF-8"));

		boolean close = (request.headers().contains("Connection", "close", true))
				|| ((request.getProtocolVersion().equals(HttpVersion.HTTP_1_1))
						&& (!(request.headers().contains("Connection", "keep-alive", true))));

		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

		response.headers().set("Content-Type", "text/plain; charset=UTF-8");
		if (claz != null)
			response.headers().set("claz", claz);
		if (errs != null)
			response.headers().set("errs", errs);

		if (!(close)) {
			response.headers().set("Content-Length", Integer.valueOf(response.content().readableBytes()));
		}

		ChannelFuture future = channel.writeAndFlush(response);

		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void clear(ChannelHandlerContext ctx) {
		AttributeKey decoderKey = AttributeKey.valueOf("decoder");
		AttributeKey paramKey = AttributeKey.valueOf("param");
		AttributeKey requestKey = AttributeKey.valueOf("request");

		ctx.channel().attr(decoderKey).set(null);
		ctx.channel().attr(requestKey).set(null);
		ctx.channel().attr(paramKey).set(null);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		messageReceived(ctx, msg);
	}
}