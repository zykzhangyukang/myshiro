package com.coderman.rent.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统模块路由
 * Created by zhangyukang on 2019/11/9 20:15
 */
@Controller
@RequestMapping("/system")
public class SystemController {


    /**
     * 跳转到y用户管理
     * @return
     */
    @GetMapping("/user")
    public String user(){
        return "system/user/user";
    }
    /**
     * 跳转到登入日志
     * @return
     */
    @GetMapping("/loginLog")
    public String loginLog(){
        return "system/monitor/loginLog";
    }
    /**
     * 跳转到后台首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "system/index/index";
    }

    /**
     * 跳转到登入页面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(){
        return "system/index/login";
    }


    /**
     * 跳转到工作台
     * @return
     */
    @GetMapping("/main")
    public String main(){
        return "system/others/main";
    }



}
