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
package com.gxhl.jts.modules.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gxhl.jts.common.model.RequestModel;
import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.job.dao.ScheduleJobLogDao;
import com.gxhl.jts.modules.job.entity.ScheduleJobLogEntity;
import com.gxhl.jts.modules.job.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

    /**
     * 分页查询
     *
     * @param params
     *
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String jobId = (String) params.get("jobId");

        Page<ScheduleJobLogEntity> page = this.selectPage(
                new RequestModel<ScheduleJobLogEntity>(params).getPage(),
                new EntityWrapper<ScheduleJobLogEntity>().like(!StringUtils.hasText(jobId), "job_id", jobId)
        );

        return new PageUtils(page);
    }

}
