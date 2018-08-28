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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gxhl.jts.common.annotation.DataFilter;
import com.gxhl.jts.common.utils.Constant;
import com.gxhl.jts.modules.sys.dao.SysOrganDao;
import com.gxhl.jts.modules.sys.entity.SysOrgan;
import com.gxhl.jts.modules.sys.service.SysOrganService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Service("sysOrganService")
public class SysOrganServiceImpl extends ServiceImpl<SysOrganDao, SysOrgan> implements SysOrganService {

    /**
     *
     * @param params
     * @return
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public List<SysOrgan> queryList(Map<String, Object> params) {
        List<SysOrgan> deptList =
                this.selectList(new EntityWrapper<SysOrgan>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER)));

        for (SysOrgan sysDeptEntity : deptList) {
            SysOrgan parentDeptEntity = this.selectById(sysDeptEntity.getParentId());
            if (parentDeptEntity != null) {
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }
        return deptList;
    }

    /**
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    /**
     *
     * @param deptId
     * @return
     */
    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //单位及下级单位ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取下级单位ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     *
     * @param subIdList
     * @param deptIdList
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }

}
