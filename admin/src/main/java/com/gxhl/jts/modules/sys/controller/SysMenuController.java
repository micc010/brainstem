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
import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.Constant;
import com.gxhl.jts.modules.sys.entity.SysMenu;
import com.gxhl.jts.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统菜单
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    public ResponseModel nav() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        return ResponseModel.ok().put("menuList", menuList);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = sysMenuService.selectList(null);
        for (SysMenu sysMenuEntity : menuList) {
            SysMenu parentMenuEntity = sysMenuService.selectById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    public ResponseModel select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return ResponseModel.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    public ResponseModel info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.selectById(menuId);
        return ResponseModel.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    public ResponseModel save(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);

        sysMenuService.insert(menu);

        return ResponseModel.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return ResponseModel.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    public ResponseModel delete(long menuId) {
        if (menuId <= 31) {
            return ResponseModel.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return ResponseModel.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return ResponseModel.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RestException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new RestException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RestException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.selectById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RestException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RestException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
