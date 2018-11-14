package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gateway.filter.CustomLocalFilters;

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
								.addResponseHeader("hello", "word").filter(new CustomLocalFilters()))
						.uri("lb://SPRINGBOOT-SERVICE"))
				.build();
	}

	@Bean
	public RouteLocator hystrixRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/hystrix/**")
						.filters(fn -> fn.stripPrefix(1).hystrix(c -> c.setName("authHystrixCommand").setFallbackUri("forward:/hystrixTimeout")))
						.uri("lb://SPRINGBOOT-SERVICE"))
				.build();
	}
	
	@GetMapping("/fallback")
    public String hystrixTimeout() throws InterruptedException {
        System.out.println("hystrixTimeout触发了断路由！");
        return "hystrixTimeout触发了断路由！";
    }


}
