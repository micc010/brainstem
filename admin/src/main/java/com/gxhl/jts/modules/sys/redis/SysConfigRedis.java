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
package com.gxhl.jts.modules.sys.redis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gxhl.jts.common.utils.RedisKeys;
import com.gxhl.jts.common.utils.RedisUtils;
import com.gxhl.jts.modules.sys.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 系统配置Redis
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Component
public class SysConfigRedis {

    @Autowired
    private RedisUtils redisUtils;

    /**
     *
     * @param config
     * @throws JsonProcessingException
     */
    public void saveOrUpdate(SysConfig config) throws JsonProcessingException {
        if (config == null) {
            return;
        }
        String key = RedisKeys.getSysConfigKey(config.getKey());
        redisUtils.set(key, config);
    }

    /**
     *
     * @param configKey
     */
    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    /**
     *
     * @param configKey
     * @return
     * @throws IOException
     */
    public SysConfig get(String configKey) throws IOException {
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfig.class);
    }
}
