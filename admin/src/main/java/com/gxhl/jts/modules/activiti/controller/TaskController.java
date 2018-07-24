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
package com.gxhl.jts.modules.activiti.controller;

import com.gxhl.jts.common.utils.PageUtils;
import com.gxhl.jts.modules.activiti.service.ActTaskService;
import com.gxhl.jts.modules.activiti.model.ProcessModel;
import com.gxhl.jts.modules.activiti.model.TaskModel;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@RequestMapping("activiti/task")
@RestController
public class TaskController {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    FormService formService;
    @Autowired
    TaskService taskService;
    @Autowired
    ActTaskService actTaskService;

    @GetMapping("goto")
    public ModelAndView gotoTask() {
        return new ModelAndView("act/task/gotoTask");
    }

    @GetMapping("/gotoList")
    PageUtils list(int offset, int limit) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .listPage(offset, limit);
        int count = (int) repositoryService.createProcessDefinitionQuery().count();
        List<Object> list = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            list.add(new ProcessModel(processDefinition));
        }

        PageUtils pageUtils = new PageUtils(list, count, limit, offset);
        return pageUtils;
    }

    @GetMapping("/form/{procDefId}")
    public void startForm(@PathVariable("procDefId") String procDefId, HttpServletResponse response)
            throws IOException {
        String formKey = actTaskService.getFormKey(procDefId, null);
        response.sendRedirect(formKey);
    }

    @GetMapping("/form/{procDefId}/{taskId}")
    public void form(@PathVariable("procDefId") String procDefId, @PathVariable("taskId") String taskId, HttpServletResponse response)
            throws IOException {
        // 获取流程XML上的表单KEY
        String formKey = actTaskService.getFormKey(procDefId, taskId);
        response.sendRedirect(formKey + "/" + taskId);
    }

    @GetMapping("/todo")
    ModelAndView todo() {
        return new ModelAndView("act/task/todoTask");
    }

    @GetMapping("/todoList")
    List<TaskModel> todoList() {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("admin").list();
        List<TaskModel> taskVOS = new ArrayList<>();
        for (Task task : tasks) {
            TaskModel taskVO = new TaskModel(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }


    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/trace/photo/{procDefId}/{execId}")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response)
            throws Exception {
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

}
