package com.gxhl.jts.modules.activiti.vo;

import lombok.Data;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

@Data
public class ProcessVO {
    private String id;
    private String name;
    private String deploymentId;

    public ProcessVO(Deployment processDefinition) {
        this.setId(processDefinition.getId());
        this.name = processDefinition.getName();
    }

    public ProcessVO(ProcessDefinition processDefinition) {
        this.setId(processDefinition.getId());
        this.name = processDefinition.getName();
        this.deploymentId = processDefinition.getDeploymentId();
    }

}
