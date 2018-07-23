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

import com.gxhl.jts.common.config.JwtProperties;
import com.gxhl.jts.common.security.model.UserContext;
import com.gxhl.jts.common.security.token.RawAccessJwtToken;
import com.gxhl.jts.modules.generator.entity.User;
import com.gxhl.jts.modules.generator.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProperties jwtProperties;
    @Autowired
    private UserService userService;

    @Autowired
    public JwtAuthenticationProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * @param authentication
     *
     * @return
     *
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtProperties.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();
        User user = userService.queryByUsername(subject);
        if (user == null || StringUtils.isEmpty(user.getUsername()))
            throw new IllegalArgumentException("Username is blank: " + user.getUsername());
        UserContext context = UserContext.create(user);
        return new JwtAuthenticationToken(context, null);
    }

    /**
     * @param authentication
     *
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
