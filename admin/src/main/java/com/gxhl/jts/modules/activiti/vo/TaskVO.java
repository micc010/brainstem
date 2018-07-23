package com.gxhl.jts.modules.activiti.vo;

import lombok.Data;
import org.activiti.engine.task.Task;

/**

 */
@Data
public class TaskVO {

    public TaskVO(Task task) {

        this.setId(task.getId());
        this.setKey(task.getTaskDefinitionKey());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setAssignee(task.getAssignee());
        this.setFormKey(task.getFormKey());
        this.setProcessId(task.getProcessInstanceId());
        this.setProcessDefinitionId(task.getProcessDefinitionId());
        this.setExecutionId(task.getExecutionId());
    }

    private String id;
    private String name;
    private String key;
    private String description;
    private String formKey;
    private String assignee;
    private String processId;
    private String processDefinitionId;
    private String executionId;

}
