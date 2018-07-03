/**
 * Copyright 2018 http://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gxhl.jts.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthenticationManager 管理认证 AuthenticationProvider 改变认证方式 UserDetailsService 改变认证的用户信息来源 ProviderManager
 * 清除返回的Authentication中的凭证信息，如密码 AccessDecisionManager
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfiguration.class);

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private CustomFilterSecurityMetadataSource securityMetadataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Web层面的配置，一般用来配置无需安全检查的路径
     *
     * @param web
     *
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        LOGGER.debug("======WebSecurity configure======");
        web.ignoring().antMatchers("/**/*.json",
                "/**/*.js",
                "/js/**",
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/theme/**",
                "/html/**",
                "/json/**",
                "/lib/**",
                "/widgets/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**",
                "/swagger-resources/**",
                "/v2/api-docs");
    }

    /**
     * Request层面的配置，对应XML Configuration中的<http>元素
     *
     * @param http
     *
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.debug("======HttpSecurity configure======");

        http.csrf().disable(); // 关闭csrf

        http.authorizeRequests()
                .mvcMatchers("/", "/index", "/login").permitAll();// 一般请求

        // 设置头
        http.headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .addHeaderWriter(new XContentTypeOptionsHeaderWriter())
                .addHeaderWriter(new XXssProtectionHeaderWriter())
                .addHeaderWriter(new CacheControlHeadersWriter())
                .addHeaderWriter(new HstsHeaderWriter());

        // 一般请求
        http.formLogin().loginPage("/login").permitAll().failureUrl("/login?error").defaultSuccessUrl("/home")
                .and()
                .logout().logoutSuccessUrl("/login?logout").permitAll()
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denie.html")
                .and()
                .authorizeRequests().expressionHandler(webSecurityExpressionHandler())
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setSecurityMetadataSource(securityMetadataSource);
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        fsi.setAuthenticationManager(authenticationManagerBean());
                        return fsi;
                    }
                })
                .and()
                .sessionManagement().invalidSessionUrl("/login?invalid").maximumSessions(1); // session管理

    }

    /**
     * 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
     *
     * @param auth
     *
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true).userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    /**
     * 密码
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    /**
     * 登录失败
     *
     * @return
     */
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/access-denie.html");
        return accessDeniedHandler;
    }

    /**
     * 登录事件
     *
     * @return
     */
    @Bean
    public org.springframework.security.authentication.event.LoggerListener authenticationLoggerListener() {
        return new org.springframework.security.authentication.event.LoggerListener();
    }

    /**
     * 通过事件
     *
     * @return
     */
    @Bean
    public org.springframework.security.access.event.LoggerListener accessEventLoggerListener() {
        return new org.springframework.security.access.event.LoggerListener();
    }

    /**
     * 访问决策器
     *
     * @return
     */
    @Bean(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(webExpressionVoter());// 启用表达式投票器
        CustomAccessDecisionManager accessDecisionManager = new CustomAccessDecisionManager(decisionVoters);
        return accessDecisionManager;
    }


    /**
     * 认证管理器
     *
     * @return
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManagerBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }

    /**
     * 失败处理器
     *
     * @return
     */
    @Bean(name = "failureHandler")
    public AuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error");
    }

    /**
     * 表达式控制器
     */
    @Bean(name = "expressionHandler")
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
//        webSecurityExpressionHandler.setDefaultRolePrefix("");
        return webSecurityExpressionHandler;
    }

    /**
     * 表达式投票器
     */
    @Bean(name = "expressionVoter")
    public WebExpressionVoter webExpressionVoter() {
        LOGGER.debug("======webExpressionVoter======");
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

}