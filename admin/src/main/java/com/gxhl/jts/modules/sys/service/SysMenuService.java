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
package com.gxhl.jts.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.gxhl.jts.modules.sys.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId
     *         父菜单ID
     * @param menuIdList
     *         用户菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId
     *         父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 获取用户菜单列表
     *
     * @param userId
     *
     * @return
     */
    List<SysMenu> getUserMenuList(Long userId);

    /**
     * 删除
     *
     * @param menuId
     */
    void delete(Long menuId);
}
