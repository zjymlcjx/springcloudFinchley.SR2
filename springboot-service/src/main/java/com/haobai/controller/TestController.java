package com.haobai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haobai.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class TestController {
	
	@PostMapping(value = "/test1")
	public String test1() {
		return "hello test1";
		
	}
	
	@GetMapping(value = "/test2")
	public String test2() {
		return "hello tets2";
		
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
	
	@RequestMapping("/hystrixTimeout")
    public String hystrixTimeout() throws InterruptedException {
        System.out.println("hystrixTimeout触发了断路由！");
        return "hystrixTimeout触发了断路由！";
    }


}
