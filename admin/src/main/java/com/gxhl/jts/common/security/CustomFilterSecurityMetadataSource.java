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

import com.gxhl.jts.modules.sys.entity.SysMenu;
import com.gxhl.jts.modules.sys.entity.SysRole;
import com.gxhl.jts.modules.sys.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 加载权限对应的角色
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Component
public class CustomFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 资源所需要的权限
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
//        LOGGER.info("Full request URL: " + fi.getFullRequestUrl());
//        LOGGER.info("Request URL: " + fi.getRequestUrl());
//        LOGGER.info("HTTP Method: " + fi.getRequest().getMethod());
//        LOGGER.info("Context path: " + fi.getRequest().getContextPath());

        Collection<ConfigAttribute> securityConfigList = new ArrayList<ConfigAttribute>();
        //在Resource表找到该资源对应的角色
        SysMenu query = new SysMenu();
        query.setUrl(fi.getRequest().getServletPath());
        List<SysRole> roleList = sysRoleService.findRoleListByPurview(query);

        if (roleList != null && roleList.size() > 0) {
            for (SysRole role :
                    roleList) {
                //以角色名称来存放
                SecurityConfig securityConfig = new SecurityConfig(role.getRole());
                securityConfigList.add(securityConfig);
                LOGGER.info("url need role " + role.getRole());
            }
        }
        return securityConfigList;
    }

    /**
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * @param arg0
     * @return
     */
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
