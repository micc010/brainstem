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
package com.gxhl.jts.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.gxhl.jts.common.form.LoginForm;
import com.gxhl.jts.modules.generator.entity.User;

import java.util.Map;

/**
 * 用户
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface UserService extends IService<User> {

    /**
     *
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     * 用户登录
     *
     * @param form 登录表单
     * @return 返回登录信息
     */
    Map<String, Object> login(LoginForm form);

}
