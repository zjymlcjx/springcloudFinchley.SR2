package com.haobai.base.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration  
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)//过期时间（秒）
public class HttpSessionConfig {
    
}
