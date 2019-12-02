package com.coderman.rbac.workflow.controller;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.workflow.service.WorkFlowService;
import com.coderman.rbac.workflow.vo.CommentEntityVo;
import com.coderman.rbac.workflow.vo.DeploymentEntityVo;
import com.coderman.rbac.workflow.vo.WorkFlowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.zip.ZipInputStream;

/**
 * 流程管理模块前端控制器
 * Created by zhangyukang on 2019/11/28 16:12
 */
@Api(value = "流程管理模块前端控制器")
@Slf4j
@Controller
@RequestMapping("/workFlow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * 跳转到流程部署管理
     * @return
     */
    @RequestMapping(value = "/workFlow",method = RequestMethod.GET)
    public String workDeploy(){
        return "workflow/work/workFlow";
    }

    /**
     * 跳转到添加流程
     * @return
     */
    @RequestMapping(value = "/workFlowAdd",method = RequestMethod.GET)
    public String workFlowAdd(){return "workflow/work/workFlowAdd";}

    /**
     * 跳转到查看流程图
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/processImage",method = RequestMethod.GET)
    public String workFlowImage(String id,Map<String,Object> map,WorkFlowVo workFlowVo){
        if(workFlowVo.getTaskId()!=null&&!"".equals(workFlowVo.getTaskId())){
            id=workFlowService.findDeployByTaskId(workFlowVo.getTaskId());
            Map positionMap=workFlowService.findProcessImagePosition(workFlowVo.getTaskId());//获取坐标信息
            map.put("positionMap",positionMap);
        }else if(workFlowVo.getSickPaperId()!=null&&!"".equals(workFlowVo.getSickPaperId())){
            workFlowVo.setTaskId(workFlowService.getTaskIdByBizKey(workFlowVo.getSickPaperId()));
            id=workFlowService.findDeployByTaskId(workFlowVo.getTaskId());
            Map positionMap=workFlowService.findProcessImagePosition(workFlowVo.getTaskId());//获取坐标信息
            map.put("positionMap",positionMap);
        } else {
            Map map2=new HashMap();
            map2.put("x",0);
            map2.put("y",0);
            map2.put("width",0);
            map2.put("height",0);
            map.put("positionMap",map2);
        }
        map.put("deployId",id);
        return "workflow/work/processImage";
    }

    /**
     * 跳转到我的代办任务
     * @return
     */
    @RequestMapping(value = "/myTask",method = RequestMethod.GET)
    public String myTask(){
        return "workflow/work/myTask";
    }

    /**
     * 查询所有流程部署信息
     * @param workFlowVo
     * @return
     */
    @ApiOperation(value = "查询所有流程部署信息",notes = "查询所有流程部署分页信息")
    @ResponseBody
    @RequestMapping("/listAllProcessDeploy")
    public PageVo<DeploymentEntityVo> listAllProcessDeploy(WorkFlowVo workFlowVo){
        PageVo pageVo = workFlowService.listAllProcessDeploy(workFlowVo);
        return pageVo;
    }

    /**
     *查询所有流程定义信息
     * @param workFlowVo
     * @return
     */
    @ApiOperation(value = "查询所有流程定义信息",notes = "查询所有流程定义分页信息")
    @ResponseBody
    @RequestMapping("/listAllProcessDefine")
    public PageVo listAllProcessDefine(WorkFlowVo workFlowVo){
        PageVo pageVo = workFlowService.listAllProcessDefine(workFlowVo);
        return pageVo;
    }
    /**
     * 流程部署
     * @param file
     * @param deploymentEntityVo
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "流程部署",notes = "流程部署")
    @ResponseBody
    @RequestMapping("/deployProcess")
    public ResultVo deployProcess(MultipartFile file,DeploymentEntityVo deploymentEntityVo) throws IOException {
        InputStream inputStream = file.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        try {
            workFlowService.deployProcess(zipInputStream,deploymentEntityVo.getName());
            return ResultVo.OK(ResultEnum.DEPLOY_PROCESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DEPLOY_PROCESS_FAIL);
        }finally {
            zipInputStream.close();
            inputStream.close();
        }
    }
    /**
     * 删除流程
     * @param workFlowVo
     * @return
     */
    @ApiOperation(value = "删除流程",notes = "删除流程")
    @ResponseBody
    @RequestMapping(value = "/delete")
    public ResultVo delete(WorkFlowVo workFlowVo){
        try {
            String id = workFlowVo.getId();
            if(id!=null&&!"".equals(id))
            workFlowService.delete(workFlowVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.info("【删除流程失败】=【流程正在运行中】");
            return ResultVo.ERROR(ResultEnum.PROCESS_DELETE_FAIL);
        }
    }
    /**
     * 删除流程部署信息
     * @param workFlowVo
     * @return
     */
    @ApiOperation(value = "批量删除流程部署信息",notes = "删除流程部署信息")
    @ResponseBody
    @GetMapping("/batchDelete")
    public ResultVo batchDelete(WorkFlowVo workFlowVo){
        try {
            workFlowService.batchDelete(workFlowVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.info("【删除流程失败】=【流程正在运行中】");
            return ResultVo.ERROR(ResultEnum.PROCESS_DELETE_FAIL);
        }
    }
    /**
     * 流程图
     * @param workFlowVo
     * @param response
     */
    @ApiOperation(value = "查看流程图",notes = "查看流程图")
    @GetMapping("/workFlowImage")
    public void workFlowImage(WorkFlowVo  workFlowVo, HttpServletResponse response){
        try {
            InputStream inputStream=workFlowService.workFlowImage(workFlowVo);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedImage img=ImageIO.read(inputStream);
            ImageIO.write(img,"PNG",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 提交请假申请
     * @return
     */
    @ApiOperation(value = "提交请假申请",notes = "提交请假申请")
    @ResponseBody
    @RequestMapping(value = "/apply",method = RequestMethod.GET)
    public ResultVo apply(WorkFlowVo workFlowVo){
        try {
            workFlowService.apply(workFlowVo);
            return ResultVo.OK(ResultEnum.APPLY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.APPLY_FAIL);
        }
    }
    /**
     * 我的代办任务
     * @return
     */
    @ApiOperation(value = "我的代办任务",notes = "我的代办任务")
    @ResponseBody
    @RequestMapping(value = "/listAllTasks",method = RequestMethod.GET)
    public PageVo listAllTasks(WorkFlowVo workFlowVo){
       return  workFlowService.listAllTasks(workFlowVo);
    }

    /**
     * 我的任务数
     * @return
     */
    @ApiOperation(value = "我的任务数",notes = "我的任务数")
    @ResponseBody
    @RequestMapping(value = "/countTask",method = RequestMethod.GET)
    public Map<String,Object> countTask(){
        Long count=workFlowService.countTask();
        Map<String,Object> map=new HashMap<>();
        map.put("count",count);
        return map;
    }

    /**
     * 通过任务Id获取请假单的信息
     * @return
     */
    @ApiOperation(value = "通过任务Id获取请假单的信息",notes = "通过任务Id获取请假单的信息")
    @ResponseBody
    @RequestMapping(value = "/loadSickPaperByTaskId",method = RequestMethod.GET)
    public ResultVo loadSickPaperByTaskId(WorkFlowVo workFlowVo){
        SickPaper sickPaper=workFlowService.loadSickPaperByTaskId(workFlowVo);
        Map<String,Object> map=new HashMap<>();
        map.put("sickPaper",sickPaper);
        //加载连线信息
        List<String> ways=workFlowService.loadProcessWaysByTaskId(workFlowVo);
        map.put("ways",ways);
        return ResultVo.OK(map);
    }

    /**
     * 加载批注
     * @param workFlowVo
     * @return
     */
    @ApiOperation(value = "加载批注",notes = "加载批注")
    @ResponseBody
    @RequestMapping(value = "/loadCommentByTaskId",method = RequestMethod.GET)
    public ResultVo loadCommentByTaskId(WorkFlowVo workFlowVo){
        List<CommentEntityVo> commentEntityVo = workFlowService.loadCommentByTaskId(workFlowVo);
        return ResultVo.OK(commentEntityVo);
    }

    /**
     * 完成任务
     * @return
     */
    @ApiOperation(value = "完成任务",notes = "完成任务")
    @ResponseBody
    @RequestMapping(value = "/doTask",method = RequestMethod.POST)
    public ResultVo doTask(WorkFlowVo workFlowVo){
        try {
            workFlowService.doTask(workFlowVo);
            return ResultVo.OK(ResultEnum.DO_TASK_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.DO_TASK_FAIL);
        }
    }

}
