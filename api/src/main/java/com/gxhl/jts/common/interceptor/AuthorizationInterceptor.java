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
package com.gxhl.jts.common.interceptor;

import com.gxhl.jts.common.annotation.Login;
import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.modules.generator.entity.Token;
import com.gxhl.jts.modules.generator.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    public static final String USER_KEY = "userId";

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }

        //token为空
        if (!StringUtils.hasText(token)) {
            throw new RestException("token不能为空");
        }

        // TODO 用jwt token 不需要查询失效 查询token信息
        Token tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new RestException("token失效，请重新登录");
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
