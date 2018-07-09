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
package com.gxhl.jts.modules.generator.controller;

import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.modules.generator.service.UserService;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.modules.generator.entity.User;
import com.gxhl.jts.common.form.RegisterForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 注册接口
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("register")
    @ApiOperation("注册")
    public ResponseModel register(@RequestBody RegisterForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        User user = new User();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setCreateTime(new Date());
        userService.insert(user);

        return ResponseModel.ok();
    }

}
