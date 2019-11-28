package com.coderman.rbac.workflow.service;

import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;

/**
 * Created by zhangyukang on 2019/11/28 16:30
 */
public interface WorkFlowService {

    /**
     * 流程部署信息
     * @return
     */
    PageVo<DeploymentEntityVo> listAllProcessDeploy(WorkFlowVo workFlowVo);

    /**
     * 流程定义信息
     * @return
     */
    PageVo<ProcessDefinitionEntityVo> listAllProcessDefine(WorkFlowVo workFlowVo);
}
