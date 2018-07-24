package com.gxhl.jts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan(basePackages = {"com.gxhl.jts.modules.*.dao"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60*2) // 设置默认的过期时间
public class AdminApplication extends SpringBootServletInitializer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    /**
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApplication.class);
    }
}
