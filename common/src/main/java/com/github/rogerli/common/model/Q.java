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
package com.github.rogerli.common.model;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.rogerli.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class Q<T> extends LinkedHashMap<String, Object> {

    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;

    public Q(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        if (params.get("pageNum") != null) {
            pageNum = Integer.parseInt((String) params.get("pageNum"));
        }
        if (params.get("pageSize") != null) {
            pageSize = Integer.parseInt((String) params.get("pageSize"));
        }

        this.put("offset", (pageNum - 1) * pageSize);
        this.put("pageNum", pageNum);
        this.put("pageSize", pageSize);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sortby = SQLFilter.sqlInject((String) params.get("sortby"));
        String sorted = SQLFilter.sqlInject((String) params.get("sorted"));
        this.put("sortby", sortby);
        this.put("sorted", sorted);

        //mybatis-plus分页
        this.page = new Page<>(pageNum, pageSize, sortby);

        //排序
        if (StringUtils.isNotBlank(sortby) && StringUtils.isNotBlank(sorted)) {
            this.page.setOrderByField(sortby);
            this.page.setAsc("ASC".equalsIgnoreCase(sorted));
        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }
}
