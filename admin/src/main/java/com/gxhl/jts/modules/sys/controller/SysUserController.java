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
package com.gxhl.jts.modules.sys.controller;

import com.gxhl.jts.common.annotation.SysLog;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.common.validator.group.InsertGroup;
import com.gxhl.jts.common.validator.group.UpdateGroup;
import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.service.SysUserRoleService;
import com.gxhl.jts.modules.sys.service.SysUserService;
import com.gxhl.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public ResponseModel info() {
        return ResponseModel.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public ResponseModel password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return ResponseModel.error("原密码不正确");
        }

        return ResponseModel.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    public ResponseModel info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.selectById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return ResponseModel.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    public ResponseModel save(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, InsertGroup.class);

        sysUserService.save(user);

        return ResponseModel.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        sysUserService.update(user);

        return ResponseModel.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    public ResponseModel delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return ResponseModel.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return ResponseModel.error("当前用户不能删除");
        }

        sysUserService.deleteBatchIds(Arrays.asList(userIds));

        return ResponseModel.ok();
    }
}
