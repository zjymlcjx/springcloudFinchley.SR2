package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.gateway.filter.CustomLocalGatewayFilter;

@SpringCloudApplication
//@SpringBootApplication
//@EnableDiscoveryClient
@RestController
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	// 开启负载均衡
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
    
	@Bean
	public RouteLocator MyRoutes(RouteLocatorBuilder builder) {
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
						.uri("lb:gateway"))
				.build();
	}
	
	@RequestMapping("/fallback")
    public String fallback() throws InterruptedException {
        System.out.println("this is fallback");
        return "this is fallback";
    }
	
	@GetMapping(value = "/test4")
	public String test4() throws InterruptedException {
		return "hello test4";
		
	}
	
	@PostMapping(value = "/test5")
	public String test5() throws InterruptedException {
		return "hello test5";
		
	}
	
	@GetMapping(value = "/testRest")
	public String testRest() {
		return restTemplate().getForObject("http://SPRINGBOOT-SERVICE/test2", String.class);
		
	}


}
