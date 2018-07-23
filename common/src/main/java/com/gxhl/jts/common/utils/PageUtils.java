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
package com.gxhl.jts.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Data
public class PageUtils implements Serializable {

    private int total;
    private int limit;
    private int totalPage;
    private int offset;
    private List<?> data;

    /**
     * 分页
     *
     * @param data   列表数据
     * @param total  总记录数
     * @param limit  每页记录数
     * @param offset 当前页数
     */
    public PageUtils(List<?> data, int total, int limit, int offset) {
        this.data = data;
        this.total = total;
        this.limit = limit;
        this.offset = offset;
        this.totalPage = (int) Math.ceil((double) total / limit);
    }

    /**
     * 分页
     *
     * @param page
     */
    public PageUtils(Page<?> page) {
        this.data = page.getRecords();
        this.total = page.getTotal();
        this.limit = page.getSize();
        this.offset = page.getCurrent();
        this.totalPage = page.getPages();
    }
}
