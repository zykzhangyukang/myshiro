package com.coderman.rbac.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基础管理模块路由
 * Created by zhangyukang on 2019/11/23 16:25
 */
@Controller
@RequestMapping("/base")
public class BaseController {


    /**
     * 跳转到登入位置统计
     * @return
     */
    @GetMapping("/loginLocationStatistics")
    public String loginLocationStatistics(){
        return "base/statistics/loginLocationStatistics";
    }

    /**
     * 跳转到登入日志统计
     * @return
     */
    @GetMapping("/loginLogStatistics")
    public String loginLogStatistics(){
        return "base/statistics/loginLogStatistics";
    }
    /**
     * 跳转到图标管理
     * @return
     */
    @GetMapping("/icon")
    public String icon(){
        return "base/icon/icon";
    }

    /**
     * 跳转到留言管理
     * @return
     */
    @GetMapping("/message")
    public String message(){
        return "base/message/message";
    }
}
