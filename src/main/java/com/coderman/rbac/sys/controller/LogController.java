package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.bean.Log;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.LogService;
import com.coderman.rbac.sys.vo.LogVo;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志前端控制器
 * Created by zhangyukang on 2019/11/15 17:40
 */
@RestController
@RequestMapping("/systemLog")
public class LogController {


    @Autowired
    private LogService logService;


    /**
     * 删除系统日志
     * @param logVo
     * @return
     */
    @RequiresPermissions({"systemLog:delete"})
    @GetMapping("/delete")
    public ResultVo delete(LogVo logVo){
        try {
            logService.delete(logVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }

    /**
     * 查询系统日志
     * @param logVo
     * @return
     */
    @GetMapping("/findPage")
    public PageVo<Log> findPage(LogVo logVo){
        PageVo<Log> page = logService.findPage(logVo);
        return page;
    }

    /**
     * 批量删除
     * @param logVo
     * @return
     */
    @RequiresPermissions({"systemLog:batchDelete"})
    @GetMapping("/batchDelete")
    public ResultVo batchDelete(LogVo logVo){
        try {
            logService.batchDelete(logVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }



}
