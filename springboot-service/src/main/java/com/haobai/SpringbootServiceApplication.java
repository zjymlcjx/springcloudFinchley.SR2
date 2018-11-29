package com.haobai;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class SpringbootServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceApplication.class, args);
	}
}
