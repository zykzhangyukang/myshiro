package com.coderman.rbac.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 任务模块路由
 * Created by zhangyukang on 2019/11/27 17:26
 */
@Controller
@RequestMapping("/job")
public class JobController {

    @GetMapping("/sickPaper")
    public String sickPaper(){
        return "job/paper/sickPaper";
    }

}
