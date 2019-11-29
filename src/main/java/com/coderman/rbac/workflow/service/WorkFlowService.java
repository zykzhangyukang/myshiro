package com.coderman.rbac.workflow.service;

import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

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


    void deployProcess(ZipInputStream zipInputStream,String name);

    /**
     * 删除流程
     * @param workFlowVo
     */
    void delete(WorkFlowVo workFlowVo);

    /**
     * 批量删除流程
     * @param workFlowVo
     */
    void batchDelete(WorkFlowVo workFlowVo);

    /**
     * 流程图片
     * @param workFlowVo
     * @return
     */
    InputStream workFlowImage(WorkFlowVo workFlowVo) throws IOException;

}
