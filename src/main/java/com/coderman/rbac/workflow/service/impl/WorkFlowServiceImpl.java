package com.coderman.rbac.workflow.service.impl;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.mapper.SickPaperMapper;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.realm.UserRealm;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.TaskEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
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
    @Transactional
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
        repositoryService.deleteDeployment(workFlowVo.getId(),true);
    }

    @Transactional
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
                repositoryService.deleteDeployment(id,true);
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
    @Transactional
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
}
