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
package com.gxhl.jts.modules.sys.controller;

import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.modules.sys.entity.SysDict;
import com.gxhl.jts.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysDictService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public ResponseModel info(@PathVariable("id") Long id) {
        SysDict dict = sysDictService.selectById(id);

        return ResponseModel.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public ResponseModel save(@RequestBody SysDict dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.insert(dict);

        return ResponseModel.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody SysDict dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return ResponseModel.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseModel delete(@RequestBody Long[] ids) {
        sysDictService.deleteBatchIds(Arrays.asList(ids));

        return ResponseModel.ok();
    }

}
