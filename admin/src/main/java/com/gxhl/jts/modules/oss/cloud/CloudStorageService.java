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

import com.gxhl.jts.common.utils.DateUtils;
import com.gxhl.jts.common.utils.UUIDUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;

/**
 * 云存储(支持七牛、阿里云)
 *
 * @author roger.li
 * @since 2018-03-30
 */
public abstract class CloudStorageService {

    /**
     * 云存储配置信息
     */
    CloudStorageConfiguration config;

    /**
     * 文件路径
     *
     * @param prefix
     *         前缀
     * @param suffix
     *         后缀
     *
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {

        //生成uuid
        String uuid = UUIDUtils.id();
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StringUtils.hasText(prefix)) {
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

    /**
     * 文件上传
     *
     * @param data
     *         文件字节数组
     * @param path
     *         文件路径，包含文件名
     *
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data
     *         文件字节数组
     * @param suffix
     *         后缀
     *
     * @return 返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     *
     * @param inputStream
     *         字节流
     * @param path
     *         文件路径，包含文件名
     *
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream
     *         字节流
     * @param suffix
     *         后缀
     *
     * @return 返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);

}
