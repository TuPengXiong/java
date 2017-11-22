package com.lovesher.netty_master.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 
	 * 一旦创建连接时就返回时间. 
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
    	System.out.println("channelActive======>"+ctx.name());
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        
        /**
         * 发送消息结束后
         */
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                System.out.println("operationComplete========>" + future.channel());
                //ctx.close();
            }
        }); // (4)
    }
    
    /**
     * 
     * channelInactive. 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	
    	super.channelInactive(ctx);
    	System.out.println("channelInactive======>"+ctx.name());
    }
    
    /**
     * 
     * channelRead. 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	super.channelRead(ctx, msg);
    	System.out.println("channelRead======>"+ctx.name());
    }
    
    /**
     * 
     * channelReadComplete 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	super.channelReadComplete(ctx);
    	System.out.println("channelReadComplete======>"+ctx.name());
    	//ctx.close();
    }
    
    /**
     * 
     * exceptionCaught. 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}