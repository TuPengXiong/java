/** 
 * Project Name:jvm 
 * File Name:Consumer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:24:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task;

import org.apache.log4j.Logger;

/**
 * ClassName:Consumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月3日 下午2:24:20 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public abstract class Consumer<E> implements Runnable {

	private E obj;
	private Producer<E> producer;

	public abstract void cosumer();

	private static final Logger logger = Logger.getLogger(Producer.class);

	public Consumer(E obj, Producer<E> producer) {
		this.obj = obj;
		this.producer = producer;
	}

	@Override
	public void run() {
		try {
			cosumer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			producer.getAtomicInteger().incrementAndGet();
		}
	}

	public Producer<E> getProducer() {
		return producer;
	}

	public void setProducer(Producer<E> producer) {
		this.producer = producer;
	}

	public E getObj() {
		return obj;
	}

	public void setObj(E obj) {
		this.obj = obj;
	}
}
