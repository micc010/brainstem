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
import com.gxhl.jts.modules.sys.entity.SysRole;
import com.gxhl.jts.modules.sys.service.SysRoleDeptService;
import com.gxhl.jts.modules.sys.service.SysRoleMenuService;
import com.gxhl.jts.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    public ResponseModel select() {
        List<SysRole> list = sysRoleService.selectList(null);

        return ResponseModel.ok().put("data", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    public ResponseModel info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.selectById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);

        return ResponseModel.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    public ResponseModel save(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.save(role);

        return ResponseModel.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return ResponseModel.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    public ResponseModel delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return ResponseModel.ok();
    }
}
