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
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 部门管理
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Data
@TableName("sys_organ")
public class SysOrgan implements Serializable {

    private static final long serialVersionUID = 1L;

    //单位ID
    @TableId
    private Long orgId;
    //单位名称
    @NotBlank(message = "单位名称不能为空")
    private String name;
    //单位代码
    private String code;
    // 简称
    private String shortName;
    // 单位类型
    private String organType;
    //排序
    private Integer orderNum;

    @TableLogic
    private Integer locked;
    private Integer checked;
    //上级单位ID，一级单位为0
    private Long parentId;
    //上级单位名称
    @TableField(exist = false)
    private String parentName;
    //上级单位简称
    @TableField(exist = false)
    private String parentShort;

    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<?> list;

}
