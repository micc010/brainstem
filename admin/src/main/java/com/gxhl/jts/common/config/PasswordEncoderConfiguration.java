package com.gxhl.jts.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码配置
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}
