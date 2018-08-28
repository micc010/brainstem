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
import com.gxhl.jts.modules.sys.entity.SysOrgan;

import java.util.List;
import java.util.Map;

/**
 * 单位管理
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysOrganService extends IService<SysOrgan> {

    /**
     * 查询单位列表
     *
     * @param map
     * @return
     */
    List<SysOrgan> queryList(Map<String, Object> map);

    /**
     * 查询下级单位列表
     *
     * @param parentId
     *         上级单位ID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 获取下级单位ID，用于数据过滤
     *
     * @param deptId
     * @return
     */
    List<Long> getSubDeptIdList(Long deptId);

}
