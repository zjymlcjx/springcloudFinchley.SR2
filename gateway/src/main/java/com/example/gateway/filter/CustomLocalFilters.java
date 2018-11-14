package com.example.gateway.filter;

import java.util.function.Consumer;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory.NameValueConfig;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 自定义局部过滤器
 * 
 * @author zjy
 *
 */
public class CustomLocalFilters implements GatewayFilter, Ordered {

	@Override
	public int getOrder() {
		//值越低优先级越高
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders reqHeaders = exchange.getRequest().getHeaders();
		HttpHeaders repHeaders = exchange.getResponse().getHeaders();
		reqHeaders.set("pre", "request");
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			repHeaders.set("post", "reponse");
		}));
	}

}
