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
package com.gxhl.jts.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gxhl.jts.modules.sys.dao.SysRoleDeptDao;
import com.gxhl.jts.modules.sys.entity.SysRoleDept;
import com.gxhl.jts.modules.sys.service.SysRoleDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与部门对应关系
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDept> implements SysRoleDeptService {

    /**
     *
     * @param roleId
     * @param deptIdList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与部门关系
        deleteBatch(new Long[]{roleId});

        if (deptIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        List<SysRoleDept> list = new ArrayList<>(deptIdList.size());
        for (Long deptId : deptIdList) {
            SysRoleDept sysRoleDeptEntity = new SysRoleDept();
            sysRoleDeptEntity.setDeptId(deptId);
            sysRoleDeptEntity.setRoleId(roleId);

            list.add(sysRoleDeptEntity);
        }
        this.insertBatch(list);
    }

    /**
     *
     * @param roleIds
     *
     * @return
     */
    @Override
    public List<Long> queryDeptIdList(Long[] roleIds) {
        return baseMapper.queryDeptIdList(roleIds);
    }

    /**
     *
     * @param roleIds
     *
     * @return
     */
    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
