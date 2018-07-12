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
package com.gxhl.jts.modules.generator.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.form.LoginForm;
import com.gxhl.jts.modules.generator.dao.UserMapper;
import com.gxhl.jts.modules.generator.entity.Token;
import com.gxhl.jts.modules.generator.entity.User;
import com.gxhl.jts.modules.generator.service.TokenService;
import com.gxhl.jts.modules.generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     *
     * @param username
     * @return
     */
    @Override
    public User queryByUsername(String username) {
        User userEntity = new User();
        userEntity.setMobile(username);
        return baseMapper.selectOne(userEntity);
    }

    /**
     *
     * @param form 登录表单
     * @return
     */
    @Override
    public Map<String, Object> login(LoginForm form) {
        User user = queryByUsername(form.getUsername());
        Assert.isNull(user, "未注册的账号");

        //密码错误
        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new RestException("账号或密码错误");
        }

        // TODO 获取登录token
        Token tokenEntity = tokenService.createToken(user.getUserId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

        return map;
    }

}
