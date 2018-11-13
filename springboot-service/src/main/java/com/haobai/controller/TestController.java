package com.haobai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
