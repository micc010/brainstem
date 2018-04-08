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
package com.github.rogerli.modules.sys.controller;

import com.github.rogerli.common.utils.Constant;
import com.github.rogerli.common.model.R;
import com.github.rogerli.modules.sys.entity.SysDept;
import com.github.rogerli.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public List<SysDept> list() {
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return deptList;
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    public R select() {
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDept root = new SysDept();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok().put("deptList", deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    public R info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysDept> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDept sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }

                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;
        }

        return R.ok().put("deptId", deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    public R info(@PathVariable("deptId") Long deptId) {
        SysDept dept = sysDeptService.selectById(deptId);

        return R.ok().put("dept", dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysDept dept) {
        sysDeptService.insert(dept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysDept dept) {
        sysDeptService.updateById(dept);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return R.error("请先删除子部门");
        }

        sysDeptService.deleteById(deptId);

        return R.ok();
    }

}
