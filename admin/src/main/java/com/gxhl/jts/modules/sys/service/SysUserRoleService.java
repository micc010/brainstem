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
import com.gxhl.jts.modules.sys.entity.SysUserRole;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 保存
     *
     * @param userId
     * @param roleIdList
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds
     * @return
     */
    int deleteBatch(Long[] roleIds);
}
