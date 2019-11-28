package com.coderman.rbac.workflow.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.ProcessDefinitionEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangyukang on 2019/11/28 16:30
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


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
        List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%")
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
        int i=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
        int j=workFlowVo.getLimit();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .deploymentIds(set).listPage(i,j);
        List<ProcessDefinitionEntityVo> processDefinitionEntityVoList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(processDefinitions)){
            for (ProcessDefinition processDefinition : processDefinitions) {
                ProcessDefinitionEntityVo processDefinitionEntityVo = new ProcessDefinitionEntityVo();
                BeanUtils.copyProperties(processDefinition,processDefinitionEntityVo);
                processDefinitionEntityVoList.add(processDefinitionEntityVo);
            }
        }
        return new PageVo(count,processDefinitionEntityVoList);
    }
}
