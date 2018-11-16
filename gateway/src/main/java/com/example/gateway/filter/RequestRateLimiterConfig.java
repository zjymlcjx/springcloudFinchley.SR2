package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * 请求限流维度
 * 
 * @author zjy
 *
 */
@Component
public class RequestRateLimiterConfig {

	/**
	 * ip地址限流
	 *
	 * @return 限流key
	 */
	@Bean
	public KeyResolver remoteAddressKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
	}

	/**
	 * 接口限流
	 *
	 * @return 限流key
	 */
	@Bean
	public KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}

	/**
	 * 用户限流
	 *
	 * @return 限流key
	 */
	@Bean
	public KeyResolver userKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("username"));
	}

}
