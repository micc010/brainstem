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
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.sys.entity.SysRole;

import java.util.Map;

/**
 * 角色
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存
     *
     * @param role
     */
    void save(SysRole role);

    /**
     * 更新
     *
     * @param role
     */
    void update(SysRole role);

    /**
     * 批量删除
     *
     * @param roleIds
     */
    void deleteBatch(Long[] roleIds);

}
