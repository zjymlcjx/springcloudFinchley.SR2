package com.haobai.base.configurations;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

	@Value("${http.maxTotal}")
	private Integer maxTotal;

	@Value("${http.defaultMaxPerRoute}")
	private Integer defaultMaxPerRoute;

	@Value("${http.connectTimeout}")
	private Integer connectTimeout;

	@Value("${http.connectionRequestTimeout}")
	private Integer connectionRequestTimeout;

	@Value("${http.socketTimeout}")
	private Integer socketTimeout;

	@Value("${http.staleConnectionCheckEnabled}")
	private boolean staleConnectionCheckEnabled;

	@Value("${http.keepAliveTimeout}")
	private Integer keepAliveTimeout;

	@Bean(name = "httpClientConnectionManager")
	public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
		httpClientConnectionManager.setMaxTotal(maxTotal);
		httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		System.out.println("http线程池配置初始化");
		return httpClientConnectionManager;
	}

	/**
	 * 实例化连接池，设置连接池管理器。 这里需要以参数形式注入上面实例化的连接池管理器
	 * 
	 * @param httpClientConnectionManager
	 * @return
	 */
	@Bean(name = "httpClientBuilder")
	public HttpClientBuilder getHttpClientBuilder(
			@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

		// HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.setConnectionManager(httpClientConnectionManager)
				.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

					@Override
					public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
						final HeaderElementIterator it = new BasicHeaderElementIterator(
								response.headerIterator(HTTP.CONN_KEEP_ALIVE));
						while (it.hasNext()) {
							final HeaderElement he = it.nextElement();
							final String param = he.getName();
							final String value = he.getValue();
							if (value != null && param.equalsIgnoreCase("timeout")) {
								try {
									return Long.parseLong(value) * 1000;
								} catch (final NumberFormatException ignore) {
								}
							}
						}
						return keepAliveTimeout;
					}
				});

		return httpClientBuilder;
	}

	// 定期关闭无效连接
	/*@Bean
	public IdleConnectionEvictor idleConnectionEvictor() {
		return new IdleConnectionEvictor();
	}*/

	@Bean
	public CloseableHttpClient getCloseableHttpClient(
			@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
		System.out.println("httpClient初始化");
		return httpClientBuilder.build();
	}

	@SuppressWarnings("deprecation")
	@Bean(name = "builder")
	public RequestConfig.Builder getBuilder() {
		RequestConfig.Builder builder = RequestConfig.custom();
		return builder.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout).setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
	}

	@Bean
	public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {
		return builder.build();
	}

}
