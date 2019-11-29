package com.coderman.rbac.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 处理流程部署信息，返回给前端的对象
 * Created by zhangyukang on 2019/11/28 16:43
 */
@Data
public class DeploymentEntityVo {

    /**流程部署Id**/
    private String id;

    /**流程部署名**/
    private  String name;
    /**部署时间**/
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时MM分ss秒")
    private  Date deploymentTime;

}
