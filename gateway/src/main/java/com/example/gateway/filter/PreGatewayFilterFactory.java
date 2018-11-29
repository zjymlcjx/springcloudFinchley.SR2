package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 前置过滤器
 * 
 * @author zjy
 *
 */
public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {

	public PreGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// grab configuration from Config object
		return (exchange, chain) -> {
			// If you want to build a "pre" filter you need to manipulate the
			// request before calling change.filter
			ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
			// use builder to manipulate the request
			return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
		};
	}

	public static class Config {
		// Put the configuration properties for your filter here
	}

}
