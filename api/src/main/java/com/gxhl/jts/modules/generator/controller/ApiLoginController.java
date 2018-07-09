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

import com.gxhl.jts.common.annotation.Login;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.modules.generator.service.UserService;
import com.gxhl.jts.common.form.LoginForm;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.modules.generator.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录接口")
public class ApiLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("login")
    @ApiOperation("登录")
    public ResponseModel login(@RequestBody LoginForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.login(form);

        return ResponseModel.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public ResponseModel logout(@ApiIgnore @RequestAttribute("userId") long userId) {
        tokenService.expireToken(userId);
        return ResponseModel.ok();
    }

}
