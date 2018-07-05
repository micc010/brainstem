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
package com.gxhl.jts.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gxhl.jts.modules.sys.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysConfigDao extends BaseMapper<SysConfig> {

    /**
     * 根据key，查询value
     */
    SysConfig queryByKey(String paramKey);

    /**
     * 根据key，更新value
     *
     * @param key
     * @param value
     * @return
     */
    int updateValueByKey(@Param("key") String key, @Param("value") String value);

}
