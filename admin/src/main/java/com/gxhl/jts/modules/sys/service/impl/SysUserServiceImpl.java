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
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gxhl.jts.common.annotation.DataFilter;
import com.gxhl.jts.common.model.RequestModel;
import com.gxhl.jts.common.utils.Constant;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.sys.dao.SysUserDao;
import com.gxhl.jts.modules.sys.entity.SysDept;
import com.gxhl.jts.modules.sys.entity.SysUser;
import com.gxhl.jts.modules.sys.service.SysDeptService;
import com.gxhl.jts.modules.sys.service.SysUserRoleService;
import com.gxhl.jts.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * @param userId
     *
     * @return
     */
    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    /**
     * @param params
     *
     * @return
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");

        Page<SysUser> page = this.selectPage(
                new RequestModel<SysUser>(params).getPage(),
                new EntityWrapper<SysUser>()
                        .like(StringUtils.hasText(username), "username", username)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );

        // TODO 获取名称。。。
        for (SysUser sysUserEntity : page.getRecords()) {
            SysDept sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
            sysUserEntity.setDeptName(sysDeptEntity.getName());
        }

        return new PageUtils(page);
    }

    /**
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUser user) {

        // TODO
        user.setCreateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    /**
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(null); // TODO
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    /**
     * @param userId
     *         用户ID
     * @param password
     *         原密码
     * @param newPassword
     *
     * @return
     */
    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        // TODO
        SysUser userEntity = new SysUser();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUser>().eq("user_id", userId).eq("password", password));
    }

}
