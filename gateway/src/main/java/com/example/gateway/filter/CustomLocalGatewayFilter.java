package com.example.gateway.filter;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 自定义局部过滤器
 * 
 * @author zjy
 *
 */
public class CustomLocalGatewayFilter implements GatewayFilter, Ordered {

	@Override
	public int getOrder() {
		// 值越低优先级越高
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse reponse = exchange.getResponse();
		String requestParam = null;
		switch (request.getMethod()) {
		case GET:
			requestParam = request.getQueryParams().toString();
			System.out.println("get请求参数：" + requestParam);
			break;
		case POST:
			Flux<DataBuffer> body = request.getBody();
			System.out.println("请求类型：" +request.getHeaders().getContentType());
			/*
			 * body.subscribe(buffer -> { byte[] bytes = new
			 * byte[buffer.readableByteCount()]; buffer.read(bytes);
			 * DataBufferUtils.release(buffer); try { String bodyString = new
			 * String(bytes, "utf-8"); System.out.println(bodyString); } catch
			 * (UnsupportedEncodingException e) { e.printStackTrace(); } });
			 */
			requestParam = DataBuffer2String(body);
			System.out.println("post请求参数：" + requestParam);

		default:
			break;
		}
		// exchange.mutate().request(request).build()
		// spring-cloud-gateway反向代理的原理是，首先读取原请求的数据，然后构造一个新的请求，将原请求的数据封装到新的请求中，然后再转发出去。上面封装之前读取了一次request
		// body,而request body只能读取一次。
		URI uri = request.getURI();
        ServerHttpRequest req = request.mutate().uri(uri).build();
        DataBuffer bodyDataBuffer = stringBuffer(requestParam);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
        req = new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };
		return chain.filter(exchange.mutate().request(req).build()).then(Mono.fromRunnable(() -> {
			reponse.getHeaders().set("post", "reponse");
		}));
	}

	public String DataBuffer2String(Flux<DataBuffer> body) {
		AtomicReference<String> bodyRef = new AtomicReference<>();
		body.subscribe(buffer -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
			DataBufferUtils.release(buffer);
			bodyRef.set(charBuffer.toString());
		});
		return bodyRef.get();

	}
	
	private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }


}
