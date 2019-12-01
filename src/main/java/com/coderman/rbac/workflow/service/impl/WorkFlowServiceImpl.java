package com.coderman.rbac.workflow.service.impl;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.mapper.SickPaperMapper;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * Created by zhangyukang on 2019/11/28 16:30
 */
@Slf4j
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SickPaperMapper sickPaperMapper;

    /**
     * 查询流程部署信息
     * @param workFlowVo
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public PageVo<DeploymentEntityVo> listAllProcessDeploy(WorkFlowVo workFlowVo) {
        long count = repositoryService.createDeploymentQuery().count();
        String name=workFlowVo.getDeployName();
        if(name==null){
            name="";
        }
        List<DeploymentEntityVo> deploymentEntityVos=null;
        int i=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
        int j=workFlowVo.getLimit();
        List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().deploymentNameLike("%" + name + "%")
        .listPage(i,j);
        if(!CollectionUtils.isEmpty(list)){
            deploymentEntityVos=new ArrayList<>();
            for (Deployment deployment : list) {
                DeploymentEntityVo deploymentEntityVo = new DeploymentEntityVo();
                BeanUtils.copyProperties(deployment,deploymentEntityVo);
                deploymentEntityVos.add(deploymentEntityVo);
            }
        }
        return new PageVo(count,deploymentEntityVos);
    }

    /**
     * 查询所有流程定义信息
     * @param workFlowVo
     * @return
     */
    @Transactional
    @Override
    public PageVo<ProcessDefinitionEntityVo> listAllProcessDefine(WorkFlowVo workFlowVo) {
        Set<String> set=new HashSet<>();
        String name=workFlowVo.getDeployName();
        if(name==null){
            name="";
        }
        long count = repositoryService.createProcessDefinitionQuery().count();
        List<Deployment> list = repositoryService.createDeploymentQuery()
                .deploymentNameLike("%" + name + "%").list();
        if(!CollectionUtils.isEmpty(list)){
            for (Deployment deployment : list) {
                set.add(deployment.getId());
            }
        }
        List<ProcessDefinitionEntityVo> processDefinitionEntityVoList=new ArrayList<>();
       if(!CollectionUtils.isEmpty(set)){
           int i=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
           int j=workFlowVo.getLimit();
           List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                   .deploymentIds(set).listPage(i,j);
           if(!CollectionUtils.isEmpty(processDefinitions)){
               for (ProcessDefinition processDefinition : processDefinitions) {
                   ProcessDefinitionEntityVo processDefinitionEntityVo = new ProcessDefinitionEntityVo();
                   BeanUtils.copyProperties(processDefinition,processDefinitionEntityVo);
                   processDefinitionEntityVoList.add(processDefinitionEntityVo);
               }
           }
       }
        return new PageVo(count,processDefinitionEntityVoList);
    }

    /**
     * 部署流程
     * @param zipInputStream
     * @param name
     */
    @Override
    public void deployProcess(ZipInputStream zipInputStream, String name) {
        Deployment deploy = repositoryService.createDeployment().addZipInputStream(zipInputStream).name(name).deploy();
        log.info("【流程部署成功】={}",deploy);
    }

    @Transactional
    @Override
    public void delete(WorkFlowVo workFlowVo) {
      repositoryService.deleteDeployment(workFlowVo.getId());
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void batchDelete(WorkFlowVo workFlowVo) {
        String ids = workFlowVo.getIds();
        List<String> idList=new ArrayList<>();
        if(ids!=null&&!"".equals(ids)){
            String[] idStrs = ids.split(",");
            for (String idStr : idStrs) {
                idList.add(idStr);
            }
        }
        if(!CollectionUtils.isEmpty(idList)){
            for (String id : idList) {
                repositoryService.deleteDeployment(id);
            }
        }
    }
    @Override
    public InputStream workFlowImage(WorkFlowVo workFlowVo) {
        String deployId = workFlowVo.getId();//流程部署ID；
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        InputStream inputStream = repositoryService.getProcessDiagram(processDefinition.getId());
        return inputStream;
    }

    @Override
    public List queryTask(String name) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(name).list();
        return list;
    }

    @Transactional
    @Override
    public void apply(WorkFlowVo workFlowVo) {
        String key= MyConstant.PROCESS_KEY;
        Map<String, Object> map=new HashMap<>();
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        map.put("userName",user.getUserName());
        runtimeService.startProcessInstanceByKey(key,key+"=>"+workFlowVo.getSickPaperId(),map);
        //修改请假单的状态
        SickPaper sickPaper = sickPaperMapper.selectByPrimaryKey(workFlowVo.getSickPaperId());
        sickPaper.setStatus(MyConstant.SICK_STATUS_APPLY);
        sickPaperMapper.updateByPrimaryKeySelective(sickPaper);
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public PageVo<TaskEntityVo> listAllTasks(WorkFlowVo workFlowVo) {
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        long count = taskService.createTaskQuery().taskAssignee(user.getUserName()).count();
        int i=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
        int j=workFlowVo.getLimit();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getUserName()).listPage(i, j);
        List<TaskEntityVo> taskEntityVos=new ArrayList<>();
        if(!CollectionUtils.isEmpty(tasks)){
            for (Task task : tasks) {
                TaskEntityVo taskEntityVo = new TaskEntityVo();
                BeanUtils.copyProperties(task,taskEntityVo);
                taskEntityVos.add(taskEntityVo);
            }
        }
        return new PageVo<>(count,taskEntityVos);
    }

    @Override
    public Long countTask() {
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        long count = taskService.createTaskQuery().taskAssignee(user.getUserName()).count();
        return count;
    }

    @Override
    public SickPaper loadSickPaperByTaskId(WorkFlowVo workFlowVo) {
        String businessKey;
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(workFlowVo.getTaskId()).singleResult();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        if(null==execution.getParentId()){
            businessKey= execution.getBusinessKey();
        }else{
            ExecutionEntity execution1 = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(execution.getParentId()).singleResult();
            businessKey=execution1.getBusinessKey();
        }
        String sickPaperId=businessKey.split("=>")[1];
        return  sickPaperMapper.selectByPrimaryKey(sickPaperId);
    }

    @Override
    public List<String> loadProcessWaysByTaskId(WorkFlowVo workFlowVo) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(workFlowVo.getTaskId()).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        ActivityImpl activity = processDefinitionEntity.findActivity(processInstance.getActivityId());
        List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        List<String> ways=new ArrayList<>();
        if(!CollectionUtils.isEmpty(outgoingTransitions)){
            for (PvmTransition outgoingTransition : outgoingTransitions) {
                ways.add(outgoingTransition.getProperty("name").toString());
            }
        }
        return ways;
    }

    @Override
    public List<CommentEntityVo> loadCommentByTaskId(WorkFlowVo workFlowVo) {
        String processInstanceId = taskService.createTaskQuery().taskId(workFlowVo.getTaskId())
                .singleResult().getProcessInstanceId();
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        List<CommentEntityVo> commentEntityVos=new ArrayList<>();
        if(!CollectionUtils.isEmpty(comments)){
            for (Comment comment : comments) {
                CommentEntityVo commentEntityVo = new CommentEntityVo();
                BeanUtils.copyProperties(comment,commentEntityVo);
                commentEntityVos.add(commentEntityVo);
            }
        }
        return commentEntityVos;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void doTask(WorkFlowVo workFlowVo) {
        String taskId = workFlowVo.getTaskId();
        String outCome = workFlowVo.getOutCome();
        String sickPaperId = workFlowVo.getSickPaperId();
        String remark = workFlowVo.getRemark();
        WebUtil.getSession().setAttribute(MyConstant.STRING_SICK_PAPER_ID,sickPaperId);
        //批注信息
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        Authentication.setAuthenticatedUserId("【"+user.getUserName()+"】");
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        taskService.addComment(taskId,processInstanceId,remark);
        //任务完成
        Map<String, Object> map=new HashMap<>();
        map.put("outcome",outCome);
        taskService.complete(taskId,map);
        //判断流程是否完成
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance==null){
            //流程结束
            SickPaper sickPaper = new SickPaper();
            sickPaper.setId(Long.parseLong(sickPaperId));
            if(outCome.equals(MyConstant.STRING_QUIT)){
                sickPaper.setStatus(MyConstant.SICK_STATUS_QUIT);
            }else{
                sickPaper.setStatus(MyConstant.SICK_STATUS_PASS);
            }
            sickPaperMapper.updateByPrimaryKeySelective(sickPaper);
        }
    }
}
