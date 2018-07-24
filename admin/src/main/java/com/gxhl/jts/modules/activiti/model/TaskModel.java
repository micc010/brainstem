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
package com.gxhl.jts.modules.activiti.model;

import lombok.Data;
import org.activiti.engine.task.Task;

/**
 * @author roger.li
 * @since 2018-03-30
 */
@Data
public class TaskModel {

    private String id;
    private String name;
    private String key;
    private String description;
    private String formKey;
    private String assignee;
    private String processId;
    private String processDefinitionId;
    private String executionId;

    /**
     *
     * @param task
     */
    public TaskModel(Task task) {
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

}
