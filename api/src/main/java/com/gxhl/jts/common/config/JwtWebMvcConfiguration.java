/**
 * @文件名称： WebMvcRestfulConfiguration.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/5
 * @作者 ： Roger
 */
package com.gxhl.jts.common.config;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Roger
 * @description
 * @create 2016/12/5 2:58
 */
@Configuration
//@ComponentScan(
//        basePackages = "com.github.rogerli",
//        useDefaultFilters = false,
//        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {RestController.class, RestControllerAdvice.class})}
//)
public class JwtWebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtWebMvcConfiguration.class);

    @Autowired
    private MessageSource messageSource;

    @Value("${spring.http.multipart.max-file-size}")
    private String maxSize;

    @Override
    public Validator getValidator() {
        LOGGER.info("======Validator======");
        LocalValidatorFactoryBean localValidatorFactoryBean =
                new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    /**
     * 优化restful
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        LOGGER.info("======RequestMappingHandlerMapping======");
        return new RestfulRequestMappingHandlerMapping();
//        return  new RequestMappingHandlerMapping();
    }

//    /**
//     * 默认视图
//     *
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        LOGGER.info("======addViewControllers======");
//        super.addViewControllers(registry);
//    }

    /**
     * Override this method to configure cross origin requests processing.
     *
     * @see CorsRegistry
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        configCorsParams(registry.addMapping("/**"));
    }

//    @Bean
//    public FilterRegistrationBean corsResponseFilter() {
//        JwtCorsRegistration corsRegistration = new JwtCorsRegistration("/**");
//        configCorsParams(corsRegistration);
//
//        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
//        configurationSource.registerCorsConfiguration("/**", corsRegistration.getCorsConfiguration());
//        CorsFilter corsFilter = new CorsFilter(configurationSource);
//        return new FilterRegistrationBean(corsFilter);
//    }

    private void configCorsParams(CorsRegistration corsRegistration) {
        corsRegistration.allowedOrigins(CrossOrigin.DEFAULT_ORIGINS)
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                .allowedHeaders(CrossOrigin.DEFAULT_ALLOWED_HEADERS)
                .allowCredentials(false)
                .maxAge(CrossOrigin.DEFAULT_MAX_AGE * 2);
    }

    /**
     * 增加resourceHandlerMapping
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info("======addResourceHandlers======");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/",
                "classpath:/public/",
                "classpath:/resources/",
                "classpath:/META-INF/resources/").setCachePeriod(31556926);
//        super.addResourceHandlers(registry);
    }

//    /**
//     * 自定义解析器实现请求参数绑定方法
//     *
//     * @param argumentResolvers
//     */
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        LOGGER.info("======addArgumentResolvers======");
//        super.addArgumentResolvers(argumentResolvers);
//    }

//    /**
//     * 自定义处理器实现返回值处理
//     *
//     * @param returnValueHandlers
//     */
//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//        LOGGER.info("======addReturnValueHandlers======");
//        super.addReturnValueHandlers(returnValueHandlers);
//    }

    /**
     * 配置messageConverter ByteArrayHttpMessageConverter ResourceHttpMessageConverter SourceHttpMessageConverter
     * AllEncompassingFormHttpMessageConverter 根据环境还可能有 romePresent：AtomFeedHttpMessageConverter，RssChannelHttpMessageConverter
     * jackson2XmlPresent：MappingJackson2XmlHttpMessageConverter jaxb2Present：Jaxb2RootElementHttpMessageConverter
     * jackson2Present：MappingJackson2HttpMessageConverter gsonPresent：GsonHttpMessageConverter
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        LOGGER.info("======MappingJackson2HttpMessageConverter======");
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        list.add(MediaType.APPLICATION_JSON);
        list.add(new MediaType("text", "html", UTF_8));
        list.add(new MediaType("text", "json"));
        converter.setSupportedMediaTypes(list);
        converters.add(converter);
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(maxSize);
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(maxSize);
        return factory.createMultipartConfig();
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//    }
//
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver commonsMultipartResolver() {
//        LOGGER.info("======CommonsMultipartResolver======");
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(5000000);
//        resolver.setMaxInMemorySize(8192);
//        resolver.setDefaultEncoding("UTF-8");
//        return resolver;
//    }


//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//        LOGGER.info("======MappingJackson2HttpMessageConverter======");
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        List<MediaType> list = new ArrayList<MediaType>();
//        list.add(new MediaType("text", "html"));
//        list.add(new MediaType("text", "html", UTF_8));
//        list.add(new MediaType("text", "json"));
//        list.add(new MediaType("text", "json", UTF_8));
//        list.add(new MediaType("text", "plain"));
//        list.add(new MediaType("text", "plain", UTF_8));
//        list.add(MediaType.APPLICATION_JSON_UTF8);
//        converter.setSupportedMediaTypes(list);
//        return converter;
//    }

}
