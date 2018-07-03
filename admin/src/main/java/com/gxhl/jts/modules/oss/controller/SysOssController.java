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
package com.gxhl.jts.modules.oss.controller;

import com.gxhl.jts.common.exception.RestException;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.Constant;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.common.validator.group.AliyunGroup;
import com.gxhl.jts.common.validator.group.QcloudGroup;
import com.gxhl.jts.common.validator.group.QiniuGroup;
import com.gxhl.jts.modules.oss.cloud.CloudStorageConfiguration;
import com.gxhl.jts.modules.oss.cloud.OSSFactory;
import com.gxhl.jts.modules.oss.entity.SysOssEntity;
import com.gxhl.jts.modules.oss.service.SysOssService;
import com.gxhl.jts.modules.sys.service.SysConfigService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOssService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    public ResponseModel config() {
        CloudStorageConfiguration config = sysConfigService.getConfigObject(KEY, CloudStorageConfiguration.class);

        return ResponseModel.ok().put("config", config);
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    public ResponseModel saveConfig(@RequestBody CloudStorageConfiguration config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

        return ResponseModel.ok();
    }


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public ResponseModel upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RestException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);

        return ResponseModel.ok().put("url", url);
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseModel delete(@RequestBody Long[] ids) {
        sysOssService.deleteBatchIds(Arrays.asList(ids));

        return ResponseModel.ok();
    }

}
