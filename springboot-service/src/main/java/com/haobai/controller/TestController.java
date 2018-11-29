package com.haobai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.haobai.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class TestController {
	
	@Bean
	// 开启负载均衡
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@PostMapping(value = "/test1")
	public String test1() {
		return "hello test1";
		
	}
	
	@GetMapping(value = "/test2")
	public String test2() {
		return "hello test2";
		
	}
	
	@PostMapping(value = "/test3")
	@HystrixCommand(commandKey="authHystrixCommand")
	public User test3(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		Thread.sleep(3000);
		if(StringUtils.isNotEmpty(request.getHeader("token"))) {
			System.out.println(request.getHeader("token"));
		}
		response.setHeader("hello", "world1");
		return user;
		
	}
	
	@GetMapping(value = "/test6")
	@HystrixCommand(commandKey="authHystrixCommand", fallbackMethod="hystrixTimeout")
	public String test3() throws InterruptedException {
		Thread.sleep(3000);
		return "hello test6";
		
	}
	
	@RequestMapping("/hystrixTimeout")
    public String hystrixTimeout() throws InterruptedException {
        System.out.println("hystrixTimeout触发了断路由！");
        return "hystrixTimeout触发了断路由！";
    }
	
	@GetMapping(value = "/testRest")
	public String testRest() {
		return restTemplate().getForObject("http://localhost:4000/test4", String.class);
		
	}


}
