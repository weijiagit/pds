package com.fykj.util.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * ClassName: IdleConnectionEvictor  
 * (定时清无效连接)
 * @author zhangtian  
 * @version
 */
public class IdleConnectionEvictor {

	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final HttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
		executeClearIdleConnection();
	}
	
	public void executeClearIdleConnection() {
		// 启动当前线程
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (!shutdown) {
						synchronized (this) {
							wait(5000);
							// 关闭失效的连接
							connMgr.closeExpiredConnections();
							connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
						}
					}
				} catch (InterruptedException ex) {
					shutdown() ;
					// 结束
					ex.printStackTrace();
				}
			}
		});
	}

	public void shutdown() {
		shutdown = true;
		executorService.shutdown();
		synchronized (this) {
			notifyAll();
		}
	}
	
}
