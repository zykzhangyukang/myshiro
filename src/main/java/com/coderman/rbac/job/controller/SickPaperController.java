package com.coderman.rbac.job.controller;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.service.SickPaperService;
import com.coderman.rbac.job.vo.SickPaperVo;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangyukang on 2019/11/27 17:31
 */
@RestController
@RequestMapping("/sickPaper")
public class SickPaperController {


    @Autowired
    private SickPaperService sickPaperService;

    @GetMapping("/findPage")
    public PageVo<SickPaper> findPage(SickPaperVo sickPaperVo){
        PageVo<SickPaper> page = sickPaperService.findPage(sickPaperVo);
        return page;
    }

    @PostMapping("/add")
    public ResultVo add(SickPaperVo sickPaperVo){
        try {
            sickPaperService.add(sickPaperVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.ADD_FAIL);
        }
    }

    @PostMapping("/update")
    public ResultVo update(SickPaperVo sickPaperVo){
        try {
            sickPaperService.update(sickPaperVo);
            return ResultVo.OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

    @GetMapping("/delete")
    public ResultVo delete(SickPaperVo sickPaperVo){
        try {
            sickPaperService.delete(sickPaperVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }



}
