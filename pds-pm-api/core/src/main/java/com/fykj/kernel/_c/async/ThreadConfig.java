package com.fykj.kernel._c.async;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="cpp.thread")
public class ThreadConfig {
	
	/**
	 * the name
	 */
	private String name;
	
	private int aliveCount;
	
	private int maxCount;
	
	private long aliveTime;
	
	public long getAliveTime() {
		return aliveTime;
	}

	public void setAliveTime(long aliveTime) {
		this.aliveTime = aliveTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAliveCount() {
		return aliveCount;
	}

	public void setAliveCount(int aliveCount) {
		this.aliveCount = aliveCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	
	
}
