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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @author roger.li
 * @since 2018-03-30
 */
public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String fullname;
    private String password;
    private String organId;
    private String organName;
    private Date lastPasswordResetDate;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    // 默认的初始化方法
    public CustomUserDetails() {

    }

    public CustomUserDetails(String username, String fullname, String password, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.authorities = authorities;
    }

    public CustomUserDetails(String id, String username, String fullname, String password, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.authorities = authorities;
    }

    public CustomUserDetails(String id, String username, String fullname, String password, String organId,
                             String organName, Date lastPasswordResetDate, String isApp, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.organId = organId;
        this.organName = organName;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }

    public CustomUserDetails(String id, String username, String fullname, String password, String organId,
                             String organName, Date lastPasswordResetDate, String isApp, Set<GrantedAuthority> authorities,
                             boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.organId = organId;
        this.organName = organName;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getOrganId() {
        return organId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomUserDetails) {
            return ((CustomUserDetails) obj).getUsername().equals(getUsername());
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getOrganName() {
        return organName;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
