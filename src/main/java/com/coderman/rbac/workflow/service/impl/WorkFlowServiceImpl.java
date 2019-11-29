package com.coderman.rbac.workflow.service.impl;

import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    /**
     * 查询流程部署信息
     * @param workFlowVo
     * @return
     */
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
}
