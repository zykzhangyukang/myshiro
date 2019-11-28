package com.coderman.rbac.workflow.vo;

import lombok.Data;

/**
 * 流程定义返回给前端的对象对象
 * Created by zhangyukang on 2019/11/28 16:59
 */
@Data
public class ProcessDefinitionEntityVo {
    private  String name;
    private  String description;
    private  String key;
    private  int version;
    private  String category;
    private  String deploymentId;
    private  String resourceName;
    private  String diagramResourceName;
    private  int suspensionState;
    private  boolean isIdentityLinksInitialized;
    private  String engineVersion;
}
