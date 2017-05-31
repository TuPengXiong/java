package com.tupengxiong.task;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;


public abstract class Producer<T> extends Thread implements InitializingBean {  
	private ScheduledExecutorService scheduledThreadPool = null;
	protected Queue<T> _QUEUE = new ConcurrentLinkedQueue<T>();
	private AtomicInteger nums = new AtomicInteger(0);
	protected Log logger = null;
	
	@Resource
	protected Configure configure;
	private String name = null;
	private int size;
	private int idle;
	private int walk;
	private int cpos;

	protected Producer(String name) {
		this.name = name;
		logger = LogFactory.getLog(name);
	}

	protected int getCpos() {
		return cpos;
	}
	
	public void setCpos(int cpos) {
		this.cpos = cpos;
	}
	
	public void setWalk(int walk) {
		if(walk <= configure.getSeed()) {
			walk = configure.getSeed();
		}
		
		this.walk = walk;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setIdle(int idle) {
		this.idle = idle;
	}
	
	public synchronized void wake() {
		if(nums.decrementAndGet() <= 0) {
			this.notify();
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		scheduledThreadPool = Executors.newScheduledThreadPool(size);
		start();
	}
	
	public ScheduledExecutorService getExecutor() {
		return scheduledThreadPool;
	}

	public final void run() {
		while(true) {
			long iCnt = walk;
			
			if(_QUEUE.isEmpty() && fill() <= 0) {
				iCnt = idle;
			}
			
			nums.set(size > _QUEUE.size() ? _QUEUE.size() : size);
			for(int i=0; i<size && !_QUEUE.isEmpty();i++) {
				Runnable the = ConsumerUserFactory.newConsumer(_QUEUE.poll(), this);
				if(the == null) {
					logger.error("ConsumerUserFactory.newConsumer err");
					nums.decrementAndGet();
					continue;
				}
				this.getExecutor().execute(the);
			}				
			
			synchronized (this) {
				try {
					if(nums.get() > 0) {
						logger.info(">>>>>>>>>>>wait()");
						wait();
					}else{
						logger.info(">>>>>>>>>>>wait(long)"+(configure.getSleep() * iCnt));
						wait(configure.getSleep() * iCnt);
					}
				} catch (InterruptedException exc) {
					logger.error(exc);
					break;
				}
				
				logger.info(">>>>>>>>>>>wait over...continue....");
			}
		}
		
		logger.warn(new StringBuffer(this.name).append(" is stop!!!!"));
	}
	
	public abstract int fill();
}

