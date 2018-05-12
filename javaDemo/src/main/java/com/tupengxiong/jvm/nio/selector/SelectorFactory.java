package com.tupengxiong.jvm.nio.selector;

import java.io.IOException;
import java.nio.channels.Selector;

public class SelectorFactory {

	/**
	 * getSelector
	 * @Title: getSelector   
	 * @Description: getSelector
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: Selector      
	 * @throws
	 */
	public static Selector getSelector() throws IOException{
		return Selector.open();
	}
}
