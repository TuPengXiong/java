package com.tupengxiong.jvm.nio.selector;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class SelectionKeyFactory {

	/**
	 * 
	 * @Title: channelRegisterToSelector   
	 * @Description: channelRegisterToSelector   
	 * @param: @param selectableChannel
	 * @param: @param selector
	 * @param: @param ops 
	 * @see java.nio.channels.Selectionkey
	 * SelectionKey.OP_CONNECT
	 * SelectionKey.OP_ACCEPT
	 * SelectionKey.OP_READ
	 * SelectionKey.OP_WRITE
	 * @param: @param att
	 * @param: @return
	 * @param: @throws ClosedChannelException      
	 * @return: SelectionKey      
	 * @throws
	 */
	public static SelectionKey channelRegisterToSelector(SelectableChannel selectableChannel,Selector selector,int ops, Object att) throws ClosedChannelException{
		return selectableChannel.register(selector, ops , att);
	}
}
