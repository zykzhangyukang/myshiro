package com.coderman.rbac.base.controller;

import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.service.LoginLogService;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图表统计
 * Created by zhangyukang on 2019/11/26 10:48
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 近期访问记录
     * @return
     */
    @GetMapping("/loadViewCount")
    public ResultVo loadViewCount(){
        Map<String, Object> data = new HashMap<>();
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(user);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        return ResultVo.OK(data);
    }


    /**
     * 用户登入位置统计
     * @return
     */
    @GetMapping("/loadLoginLocation")
    public ResultVo loadLoginLocation(){
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> locationCount = this.loginLogService.loadLoginLocation();
        data.put("locationCount", locationCount);
        return ResultVo.OK(data);
    }

}
