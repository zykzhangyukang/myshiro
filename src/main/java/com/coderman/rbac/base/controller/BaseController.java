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
