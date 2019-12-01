package com.coderman.rbac.workflow.service;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.vo.CommentEntityVo;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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


    /**
     * 部署流程
     * @param zipInputStream
     * @param name
     */
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


    /**
     * 查询代办任务
     * @param name
     * @return
     */
     List<Task> queryTask(String name);

    /**
     * 提交申请，启动流程
     * @param workFlowVo
     */
    void apply(WorkFlowVo workFlowVo);

    /**
     * 代办任务
     * @return
     */
    PageVo listAllTasks(WorkFlowVo workFlowVo);

    /**
     * 我的任务数
     * @return
     */
    Long countTask();

    /**
     * 加载请假单的信息
     * @param workFlowVo
     * @return
     */
    SickPaper loadSickPaperByTaskId(WorkFlowVo workFlowVo);

    /**
     * 加载连线信息
     * @param workFlowVo
     * @return
     */
    List<String> loadProcessWaysByTaskId(WorkFlowVo workFlowVo);

    /**
     * 加载批注信息
     * @param workFlowVo
     */
    List<CommentEntityVo> loadCommentByTaskId(WorkFlowVo workFlowVo);

    /**
     * 完成任务
     * @param workFlowVo
     */
    void doTask(WorkFlowVo workFlowVo);
}
