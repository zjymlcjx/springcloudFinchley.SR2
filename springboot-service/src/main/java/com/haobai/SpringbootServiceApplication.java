package com.haobai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringbootServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceApplication.class, args);
	}
}
