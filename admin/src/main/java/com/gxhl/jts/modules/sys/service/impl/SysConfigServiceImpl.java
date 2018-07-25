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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.model.RequestModel;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.sys.dao.SysConfigDao;
import com.gxhl.jts.modules.sys.entity.SysConfig;
import com.gxhl.jts.modules.sys.utils.SysConfigRedisUtils;
import com.gxhl.jts.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigRedisUtils sysConfigRedisUtils;

    /**
     * 分页查询
     *
     * @param params
     *
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        Page<SysConfig> page = this.selectPage(
                new RequestModel<SysConfig>(params).getPage(),
                new EntityWrapper<SysConfig>()
                        .like(StringUtils.hasText(key), "key", key)
                        .eq("status", 1)
        );

        return new PageUtils(page);
    }

    /**
     * 保存
     *
     * @param config
     *
     * @throws JsonProcessingException
     */
    @Override
    public void save(SysConfig config) throws JsonProcessingException {
        this.insert(config);
        sysConfigRedisUtils.saveOrUpdate(config);
    }

    /**
     * 更新
     *
     * @param config
     *
     * @throws JsonProcessingException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfig config) throws JsonProcessingException {
        this.updateById(config);
        sysConfigRedisUtils.saveOrUpdate(config);
    }

    /**
     * 更新
     *
     * @param key
     * @param value
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
        sysConfigRedisUtils.delete(key);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysConfig config = this.selectById(id);
            sysConfigRedisUtils.delete(config.getKey());
        }

        this.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 获得值
     *
     * @param key
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public String getValue(String key) throws IOException {
        SysConfig config = sysConfigRedisUtils.get(key);
        if (config == null) {
            config = baseMapper.queryByKey(key);
            sysConfigRedisUtils.saveOrUpdate(config);
        }

        return config == null ? null : config.getValue();
    }

    /**
     * 获得对象
     *
     * @param key
     *         key
     * @param clazz
     * @param <T>
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) throws IOException {
        String value = getValue(key);
        if (StringUtils.hasText(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RestException("获取参数失败");
        }
    }
}
