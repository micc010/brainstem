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
package com.github.rogerli.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 使用自定义的访问决策器，在decide方法中判断资源的权限与用户的权限是否相同
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class CustomAccessDecisionManager extends AbstractAccessDecisionManager {

    private final Logger LOGGER = LoggerFactory
            .getLogger(CustomAccessDecisionManager.class);

    public CustomAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    /**
     * 权限控制的逻辑
     *
     * @param authentication   用户的认证对象
     * @param object           the secured object
     * @param configAttributes 访问该资源所要的权限
     */
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        LOGGER.debug("======CustomAccessDecisionManager decide======");

        //如果资源要的权限为空，则可以访问
        if (configAttributes == null) {
            return;
        }

        for (ConfigAttribute configAttribute :
                configAttributes) {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (configAttribute.getAttribute().equals(ga.getAuthority())) { // 资源要的权限 = 用户的权限
                    LOGGER.debug("======User has permission======");
                    return;
                }
            }
        }

        LOGGER.debug("======AccessDecision Dendied======");
        throw new AccessDeniedException("======Access Dendied======");
    }

    public boolean supports(ConfigAttribute arg0) {
        LOGGER.debug("======ConfigAttribute supports======");
        return true;
    }

    public boolean supports(Class<?> arg0) {
        LOGGER.debug("======Class supports======");
        return true;
    }

}
