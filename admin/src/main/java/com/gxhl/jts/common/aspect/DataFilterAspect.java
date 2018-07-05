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
package com.gxhl.jts.common.aspect;

import com.gxhl.jts.common.annotation.DataFilter;
import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.utils.Constant;
import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.service.SysDeptService;
import com.gxhl.jts.modules.sys.service.SysRoleDeptService;
import com.gxhl.jts.modules.sys.service.SysUserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据过滤，切面处理类
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Aspect
@Component
public class DataFilterAspect {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     *
     */
    @Pointcut("@annotation(com.gxhl.jts.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    /**
     * 数据过滤
     *
     * @param point
     *
     * @throws Throwable
     */
    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof Map) {
            SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            //如果不是超级管理员，则进行数据过滤
            if (user.getUserId() != Constant.SUPER_ADMIN) {
                Map map = (Map) params;
                map.put(Constant.SQL_FILTER, getSQLFilter(user, point));
            }

            return;
        }

        throw new RestException("数据权限接口，只能是Map类型参数，且不能为NULL");
    }

    /**
     * 获取数据过滤的SQL
     *
     * @param user
     * @param point
     *
     * @return
     */
    private String getSQLFilter(SysUser user, JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if (StringUtils.hasText(tableAlias)) {
            tableAlias += ".";
        }

        //用户部门ID列表
        Set<Long> deptIdList = new HashSet<>();
        deptIdList.add(user.getDeptId());

        //用户角色对应的部门ID列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        if (roleIdList.size() > 0) {
            List<Long> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
            deptIdList.addAll(userDeptIdList);
        }

        //用户子部门ID列表
        if (dataFilter.subDept()) {
            List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
            deptIdList.addAll(subDeptIdList);
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");
        sqlFilter.append(tableAlias).append("dept_id in(").append(StringUtils.collectionToDelimitedString(deptIdList, ",")).append(")");

        //没有本部门数据权限，也能查询本人数据
        if (dataFilter.user()) {
            sqlFilter.append(" or ").append(tableAlias).append("user_id=").append(user.getUserId());
        }
        sqlFilter.append(")");

        return sqlFilter.toString();
    }

}