package com.coderman.rbac.workflow.vo;

import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/28 16:34
 */
@Data
public class WorkFlowVo {

    private String id;

    private String ids;

    private int page;
    private int limit;

    /**流程部署名**/
    private String deployName;
}
