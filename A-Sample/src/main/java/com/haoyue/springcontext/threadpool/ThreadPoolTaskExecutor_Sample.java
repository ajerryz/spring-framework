package com.haoyue.springcontext.threadpool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;

/**
 * @author jerry zhang
 * @since 2025/2/23 21:59
 */
public class ThreadPoolTaskExecutor_Sample {
	public static void main(String[] args) {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(2);
		threadPoolTaskExecutor.setThreadFactory(Executors.defaultThreadFactory());
		threadPoolTaskExecutor.initialize();
	}


}
