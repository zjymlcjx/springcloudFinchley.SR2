package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gateway.filter.CustomLocalGatewayFilter;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/spring/**")
						.filters(fn -> fn.stripPrefix(1).addRequestHeader("token", "111111")
								.addResponseHeader("hello", "word").filter(new CustomLocalGatewayFilter()))
						.uri("lb://SPRINGBOOT-SERVICE"))
				.build();
	}

	@Bean
	public RouteLocator hystrixRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/hystrix/**")
						.filters(fn -> fn.stripPrefix(1).hystrix(c -> c.setName("hystrixCommand").setFallbackUri("forward:/fallback")))
						.uri("lb://SPRINGBOOT-SERVICE"))
				.build();
	}
	
	@RequestMapping("/fallback")
    public String fallback() throws InterruptedException {
        System.out.println("this is fallback");
        return "this is fallback";
    }


}
