package com.example.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory.ResponseAdapter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultClientResponse;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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

		/*String token = exchange.getRequest().getHeaders().getFirst("Token");
		ServerHttpResponse originalResponse = exchange.getResponse();
		DataBufferFactory bufferFactory = originalResponse.bufferFactory();
		if(StringUtils.isEmpty(token)){
			//exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
					if (body instanceof Flux) {
						Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
						return super.writeWith(fluxBody.map(dataBuffer -> {
							// probably should reuse buffers
							byte[] content = new byte[dataBuffer.readableByteCount()];
							dataBuffer.read(content);
							//释放掉内存
							DataBufferUtils.release(dataBuffer);
							String s = new String(content, Charset.forName("UTF-8"));
							s = "无权限";
							//TODO，s就是response的值，想修改、查看就随意而为了
							byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
							return bufferFactory.wrap(uppedContent);
						}));
					}
					// if body is not a flux. never got there.
					return super.writeWith(body);
				}
			};
			return exchange.mutate().response(decoratedResponse).build().getResponse().setComplete();
		}
        // replace response with decorator
		return chain.filter(exchange);*/
		/*ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {

			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

				Class inClass = config.getInClass();
				Class outClass = config.getOutClass();

				String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
				HttpHeaders httpHeaders = new HttpHeaders();
				//explicitly add it in this way instead of 'httpHeaders.setContentType(originalResponseContentType)'
				//this will prevent exception in case of using non-standard media types like "Content-Type: image"
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);
				ResponseAdapter responseAdapter = new ResponseAdapter(body, httpHeaders);
				DefaultClientResponse clientResponse = new DefaultClientResponse(responseAdapter, ExchangeStrategies.withDefaults());

				//TODO: flux or mono
				Mono modifiedBody = clientResponse.bodyToMono(inClass)
						.flatMap(originalBody -> config.rewriteFunction.apply(exchange, originalBody));

				BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
				CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());
				return bodyInserter.insert(outputMessage, new BodyInserterContext())
						.then(Mono.defer(() -> {
							long contentLength1 = getDelegate().getHeaders().getContentLength();
							Flux<DataBuffer> messageBody = outputMessage.getBody();
							//TODO: if (inputStream instanceof Mono) {
								HttpHeaders headers = getDelegate().getHeaders();
								if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
									messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
								}
							// }
							//TODO: use isStreamingMediaType?
							return getDelegate().writeWith(messageBody);
						}));
			}

			@Override
			public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
				return writeWith(Flux.from(body)
						.flatMapSequential(p -> p));
			}
		};*/

		return null;

	}

}
