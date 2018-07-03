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

    //总记录数
    private int total;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int pageNum;
    //列表数据
    private List<?> data;

    /**
     * 分页
     *
     * @param data
     *         列表数据
     * @param total
     *         总记录数
     * @param pageSize
     *         每页记录数
     * @param pageNum
     *         当前页数
     */
    public PageUtils(List<?> data, int total, int pageSize, int pageNum) {
        this.data = data;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalPage = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 分页
     *
     * @param page
     */
    public PageUtils(Page<?> page) {
        this.data = page.getRecords();
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.pageNum = page.getCurrent();
        this.totalPage = page.getPages();
    }
}
