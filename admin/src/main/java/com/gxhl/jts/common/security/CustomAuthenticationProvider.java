package com.gxhl.jts.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private CustomUserDetailService userService;

    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(username);
        // TODO
        if (true) {
            throw new BadCredentialsException("错误的验证码");
        }

        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("错误的密码");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    /**
     *
     * @param arg0
     * @return
     */
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }


}

