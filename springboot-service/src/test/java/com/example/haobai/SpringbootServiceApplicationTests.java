package com.example.haobai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haobai.SpringbootServiceApplication;
import com.haobai.base.utils.RedisUtils;
import com.haobai.entity.User;
import com.haobai.repository.TestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootServiceApplication.class)
public class SpringbootServiceApplicationTests {

	/*@Autowired
	RedisUtils redisUtils;
	
	@Autowired
	TestRepository testRepository;
	
	@Test
	public void testRedis() {
		redisUtils.set("zjy", "zjy");
	}
	@Test
	public void testJpa() {
		User user = new User("zjy", 200);
		testRepository.save(user);
	}*/

}
