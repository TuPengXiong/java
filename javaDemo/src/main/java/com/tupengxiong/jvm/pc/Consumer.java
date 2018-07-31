/** 
 * Project Name:jvm 
 * File Name:Consumer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:24:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.pc;  
/** 
 * ClassName:Consumer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月3日 下午2:24:20 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public abstract class Consumer<E> implements Runnable{

	private E obj;
	private Producer<E> producer;
	public abstract void cosumer();
	
	public Consumer(E obj,Producer<E> producer){
		this.obj = obj;
		this.producer = producer;
	}
	@Override
	public void run() {
		try {
			cosumer();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
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
  