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
import com.gxhl.jts.modules.sys.entity.SysRoleDept;

import java.util.List;

/**
 * 角色与部门对应关系
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysRoleDeptService extends IService<SysRoleDept> {

    /**
     * 保存
     *
     * @param roleId
     * @param deptIdList
     */
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色ID，获取部门ID列表
     *
     * @param roleIds
     *
     * @return
     */
    List<Long> queryDeptIdList(Long[] roleIds);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds
     *
     * @return
     */
    int deleteBatch(Long[] roleIds);
}
