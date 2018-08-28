package com.gxhl.jts.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 国际化配置
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Configuration
public class LocalizationConfiguration {

    @Value("${spring.messages.basename}")
    private String baseName;
    @Value("${spring.messages.cache-seconds}")
    private int cacheSeconds;
    @Value("${spring.messages.encoding}")
    private String encoding;

    @Bean(name = "messageSource")
    public MessageSourceAccessor messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(encoding);
        messageSource.setBasename(baseName);
        messageSource.setCacheSeconds(cacheSeconds);
        MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource);
        return accessor;
    }

}