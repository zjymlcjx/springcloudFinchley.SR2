package com.example.gateway.filter;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器
 * 
 * @author ZJY
 *
 */
@Component
public class CustomGlobalGatewayFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String token = exchange.getRequest().getHeaders().getFirst("Token");
		if (StringUtils.isEmpty(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			/*ServerHttpResponse originalResponse = exchange.getResponse();
			DataBufferFactory bufferFactory = originalResponse.bufferFactory();
			ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
					if (body instanceof Flux) {
						Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
						return super.writeWith(fluxBody.map(dataBuffer -> {
							// probably should reuse buffers
							byte[] content = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(content);
							// 释放掉内存
							DataBufferUtils.release(dataBuffer);
							String s = new String(content, Charset.forName("UTF-8"));
							s = "无权限";
							// TODO，s就是response的值，想修改、查看就随意而为了
							byte[] uppedContent = new String(s.getBytes(), Charset.forName("UTF-8")).getBytes();
							return bufferFactory.wrap(uppedContent);
						}));
					}
					// if body is not a flux. never got there.
					return super.writeWith(body);
				}
			};
			return exchange.mutate().response(decoratedResponse).build().getResponse().setComplete();*/
			return exchange.getResponse().setComplete();
		}
		return chain.filter(exchange);
	}

}
