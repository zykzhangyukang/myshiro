package com.coderman.rbac.sys.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by zhangyukang on 2019/11/27 15:48
 */

@Configuration
public class ActivitiConfig  {


    @Autowired
    private DataSource dataSource;

    /**
     * 初始化配置
     * @return
     */
    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }

    /**
     * 流程引擎
     */
    @Bean
    public ProcessEngine processEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }



    @Bean
    public RepositoryService repositoryService() throws Exception{
        return processEngine().getRepositoryService();
    }
    @Bean
    public RuntimeService runtimeService() throws Exception{
        return processEngine().getRuntimeService();
    }
    @Bean
    public TaskService taskService() throws Exception{
        return processEngine().getTaskService();
    }
    @Bean
    public HistoryService historyService() throws Exception{
        return processEngine().getHistoryService();
    }

}
