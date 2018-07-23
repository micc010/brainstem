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
import com.gxhl.jts.modules.generator.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/api")
@Api(tags = "测试接口")
public class ApiTestController {

    /**
     * @param user
     *
     * @return
     */
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息", response = ResponseModel.class)
    public ResponseModel userInfo(@ApiIgnore User user) {
        return ResponseModel.ok().put("user", user);
    }

    /**
     * @param userId
     *
     * @return
     */
    @GetMapping("userId")
    @ApiOperation(value = "获取用户ID", response = ResponseModel.class)
    public ResponseModel userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId) {
        return ResponseModel.ok().put("userId", userId);
    }

}
