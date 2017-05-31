package com.tupengxiong.task;

import org.springframework.stereotype.Component;

/**
 * Configure
 * @description：
 * @author JChengc
 * 
 */
@Component
public class Configure {
	private long sleep = 1L;
	private int seed = 10;

	public long getSleep() {
		return sleep;
	}

	public void setSleep(long sleep) {
		this.sleep = sleep;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
