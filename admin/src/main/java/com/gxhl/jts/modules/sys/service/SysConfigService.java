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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.sys.entity.SysConfig;

import java.io.IOException;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author roger.li
 * @since 2018-03-30
 */
public interface SysConfigService extends IService<SysConfig> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    void save(SysConfig config) throws JsonProcessingException;

    /**
     * 更新配置信息
     */
    void update(SysConfig config) throws JsonProcessingException;

    /**
     * 根据key，更新value
     */
    void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    void deleteBatch(Long[] ids);

    /**
     * 根据key，获取配置的value值
     *
     * @param key
     *         key
     */
    String getValue(String key) throws IOException;

    /**
     * 根据key，获取value的Object对象
     *
     * @param key
     *         key
     * @param clazz
     *         Object对象
     */
    <T> T getConfigObject(String key, Class<T> clazz) throws IOException;

}
