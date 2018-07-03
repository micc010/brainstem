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


import com.gxhl.jts.common.annotation.SysLog;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.modules.sys.entity.SysConfig;
import com.gxhl.jts.modules.sys.service.SysConfigService;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	public ResponseModel list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);

		return ResponseModel.ok().put("page", page);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	public ResponseModel info(@PathVariable("id") Long id){
		SysConfig config = sysConfigService.selectById(id);
		
		return ResponseModel.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	public ResponseModel save(@RequestBody SysConfig config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return ResponseModel.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	public ResponseModel update(@RequestBody SysConfig config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return ResponseModel.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	public ResponseModel delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return ResponseModel.ok();
	}

}
