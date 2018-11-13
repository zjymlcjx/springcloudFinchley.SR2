package com.haobai.base.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdleConnectionEvictor extends Thread {

	@Autowired
	private HttpClientConnectionManager connMgr;

	@Value("${http.idleTimeout}")
	private Integer idleTimeout;

	private volatile boolean shutdown;

	public IdleConnectionEvictor() {
		super();
		System.out.println("启动清理无效连接的线程");
		super.start();
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 该方法关闭超过连接保持时间的连接，并从池中移除。
					connMgr.closeExpiredConnections();
//					System.out.println("关闭超过连接保持时间的连接");
					// 该方法关闭空闲时间(30s)超过timeout的连接，空闲时间从交还给连接池时开始，不管是否已过期，超过空闲时间则关闭
					connMgr.closeIdleConnections(idleTimeout, TimeUnit.SECONDS);
//					System.out.println("关闭空闲时间超过timeout的连接");
				}
			}
		} catch (InterruptedException ex) {
			// 结束
		}
	}

	@PreDestroy
	// 关闭清理无效连接的线程
	public void shutdown() {
//		System.out.println("关闭清理无效连接的线程");
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
