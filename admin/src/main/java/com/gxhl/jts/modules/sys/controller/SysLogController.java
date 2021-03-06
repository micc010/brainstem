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
import com.gxhl.jts.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 系统日志
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysLogService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }

}
