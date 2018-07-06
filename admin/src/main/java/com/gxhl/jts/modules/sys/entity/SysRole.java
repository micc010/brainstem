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
package com.gxhl.jts.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    private Long roleId;

    /**
     * 角色标识
     */
    private String role;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 权限清单
     */
    @TableField(exist = false)
    private List<Long> menuIdList;

    /**
     * 部门清单
     */
    @TableField(exist = false)
    private List<Long> deptIdList;

    /**
     * 创建时间
     */
    private Date createTime;
}
