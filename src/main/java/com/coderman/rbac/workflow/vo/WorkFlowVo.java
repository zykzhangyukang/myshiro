package com.coderman.rbac.workflow.vo;

import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/28 16:34
 */
@Data
public class WorkFlowVo {

    /**流程部署ID**/
    private String id;

    private String ids;

    private int page;

    private int limit;

    /**流程部署名**/
    private String deployName;

    /**任务办理人**/
    private String assignee;

    /**请假单ID**/
    private String sickPaperId;

    /**任务ID**/
    private String taskId;

    /**任务完成去向**/
    private String outCome;

    /**任务批注信息**/
    private String remark;

}
