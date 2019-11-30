package com.coderman.rbac.sys.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by zhangyukang on 2019/11/27 16:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestActiviti {

    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void deploy(){
        InputStream inputStream = this.getClass().getResourceAsStream("/process/SickProcess.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deploy = repositoryService.createDeployment().name("HelloWord").addZipInputStream(zipInputStream).deploy();
        System.out.println("流程部署成功："+deploy);
    }

    @Test
    public void deploy2(){
        Deployment deploy = repositoryService.createDeployment().name("流程HelloWord")
                .addClasspathResource("process/HelloWord.xml").addClasspathResource("process/HelloWord.png").deploy();
        System.out.println("流程部署成功"+deploy);
    }

    /**
     * 开始流程
     */
    @Test
    public void start(){
        Map<String, Object> map=new HashMap<>();
        map.put("userName","justin");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1",map);
        System.out.println("流程启动："+processInstance);
    }



}
