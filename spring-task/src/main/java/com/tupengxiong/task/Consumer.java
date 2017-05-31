package com.tupengxiong.task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Consumer<T> implements Runnable {
	protected final Log logger = LogFactory.getLog(getName());
	private Producer<?> producer;
	protected T dto;
	
	protected abstract String getName();
	
	public Consumer(T dto, Producer<?> producer) {
		this.producer = producer;
		this.dto = dto;
	}
	
	@Override
	public final void run() {
		try{
			doImpl();
		}catch(Exception exc) {
			logger.error(exc);
		}finally{
			producer.wake();
		}
	}
	
	public abstract void doImpl(); 
}
