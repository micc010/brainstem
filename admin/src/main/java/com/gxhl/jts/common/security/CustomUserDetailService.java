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

import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.service.SysRoleService;
import com.gxhl.jts.modules.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据用户查找权限
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * @param username
     *
     * @return
     *
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            LOGGER.error("Username not found");
            throw new UsernameNotFoundException("用户名为空");
        }

        SysUser login = sysUserService.findByUsername(username);
        if (login == null) {
            LOGGER.error("User not exists");
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        sysRoleService.findRoleListByLogin(login).forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRole())));

        return new CustomUserDetails(login.getUserId(), login.getUsername(), login.getFullName(), null, login.getDeptId(), null, new Date(), Whether.no.value(), authorities);
    }

}
