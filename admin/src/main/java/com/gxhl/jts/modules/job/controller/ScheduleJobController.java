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
package com.gxhl.jts.modules.job.controller;

import com.gxhl.jts.common.annotation.SysLog;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.common.validator.ValidatorUtils;
import com.gxhl.jts.modules.job.entity.ScheduleJobEntity;
import com.gxhl.jts.modules.job.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务
 *
 * @author roger.li
 * @since 2018-03-30
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    public ResponseModel list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobService.queryPage(params);

        return ResponseModel.ok().put("page", page);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    public ResponseModel info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.selectById(jobId);

        return ResponseModel.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    public ResponseModel save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.save(scheduleJob);

        return ResponseModel.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    public ResponseModel update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return ResponseModel.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    public ResponseModel delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return ResponseModel.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    public ResponseModel run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return ResponseModel.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    public ResponseModel pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return ResponseModel.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    public ResponseModel resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return ResponseModel.ok();
    }

}
