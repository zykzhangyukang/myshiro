package com.coderman.rbac.workflow.vo;

import lombok.Data;

import java.util.Date;

/**
 * 处理流程部署信息，返回给前端的对象
 * Created by zhangyukang on 2019/11/28 16:43
 */
@Data
public class DeploymentEntityVo {
    /**流程部署名**/
    private  String name;
    /**部署时间**/
    private  Date deploymentTime;

}
