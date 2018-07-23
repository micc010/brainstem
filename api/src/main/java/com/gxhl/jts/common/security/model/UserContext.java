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
package com.gxhl.jts.common.security.model;

import com.gxhl.jts.modules.generator.entity.User;
import org.springframework.util.StringUtils;

/**
 * @author roger.li
 * @since 2018-03-30
 */
public class UserContext {

    private final User user;

    /**
     * @param user
     */
    private UserContext(User user) {
        this.user = user;
    }

    /**
     * @param user
     *
     * @return
     */
    public static UserContext create(User user) {
        if (user == null || StringUtils.isEmpty(user.getUsername()))
            throw new IllegalArgumentException("Username is blank: " + user.getUsername());
        return new UserContext(user);
    }

    /**
     * @return
     */
    public User getUser() {
        return user;
    }
}
