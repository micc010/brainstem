package com.gxhl.jts.modules.activiti.utils;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**

 */
@Component
public class ActivitiUtils {

    @Autowired
    TaskService taskService;
    @Autowired
    RuntimeService runtimeService;

    /**
     * 根据taskId查找businessKey
     *
     * @param taskId
     * @return
     */
    public String getBusinessKeyByTaskId(String taskId) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        ProcessInstance pi = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        return pi.getBusinessKey();
    }

    /**
     *
     * @param taskId
     * @return
     */
    public Task getTaskByTaskId(String taskId) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        return task;
    }
}
