package com.gxhl.jts.common.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60*2) // 设置默认的过期时间
public class RedisSessionConfiguration {

}
