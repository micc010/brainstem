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
package com.gxhl.jts.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.gxhl.jts.common.exception.RestException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class AliyunCloudStorageService extends CloudStorageService {

    private OSSClient client;

    /**
     * 构造
     *
     * @param config
     */
    public AliyunCloudStorageService(CloudStorageConfiguration config) {
        this.config = config;

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    /**
     * 上传
     *
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 上传
     *
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new RestException("上传文件失败，请检查配置信息", e);
        }

        return config.getAliyunDomain() + "/" + path;
    }

    /**
     * 上传
     *
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return
     */
    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    /**
     * 上传
     *
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return
     */
    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }

}
