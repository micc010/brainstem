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
package com.gxhl.jts.modules.activiti.service;

import com.gxhl.jts.modules.activiti.entity.ProcessInstance;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author roger.li
 * @since 2018-03-30
 */
public interface ActTaskService {
    List<ProcessInstance> listTodo(ProcessInstance act);

    void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars);

    void complete(String taskId, Map<String, Object> vars);

    String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars);

    String getFormKey(String procDefId, String taskDefKey);

    InputStream tracePhoto(String processDefinitionId, String executionId);
}
