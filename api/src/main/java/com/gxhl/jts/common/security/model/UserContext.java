package com.gxhl.jts.common.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class UserContext {

    private int status;
    private final String username;
    private final String organId;
    private final List<GrantedAuthority> authorities;

    private UserContext(String username, String organId, List<GrantedAuthority> authorities) {
        this.username = username;
        this.organId = organId;
        this.authorities = authorities;
    }
    
    public static UserContext create(String username, String organId, List<GrantedAuthority> authorities) {
        if (StringUtils.isEmpty(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, organId, authorities);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getOrganId() {return organId;}

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
