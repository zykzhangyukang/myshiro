package com.coderman.rbac.workflow.controller;

import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 流程管理模块前端控制器
 * Created by zhangyukang on 2019/11/28 16:12
 */
@Controller
@RequestMapping("/workFlow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * 跳转到流程部署管理
     * @return
     */
    @GetMapping("/workDeploy")
    public String workDeploy(){
        return "workflow/work/workDeploy";
    }

    @ResponseBody
    @RequestMapping("/listAllProcessDeploy")
    public PageVo<DeploymentEntityVo> listAllProcessDeploy(WorkFlowVo workFlowVo){
        PageVo pageVo = workFlowService.listAllProcessDeploy(workFlowVo);
        return pageVo;
    }

    @ResponseBody
    @RequestMapping("/listAllProcessDefine")
    public PageVo listAllProcessDefine(WorkFlowVo workFlowVo){
        PageVo pageVo = workFlowService.listAllProcessDefine(workFlowVo);
        return pageVo;
    }


}
