package com.coderman.rbac.sys.controller;

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
     * 跳转到部门管理页面(左边)
     * @return
     */
    @GetMapping("/deptLeft")
    public String deptLeft(){
        return "system/dept/deptLeft";
    }
    /**
     * 跳转到部门管理页面（右边）
     * @return
     */
    @GetMapping("/deptRight")
    public String deptRight(){
        return "system/dept/deptRight";
    }
    /**
     * 跳转到部门管理页面
     * @return
     */
    @GetMapping("/dept")
    public String dept(){
        return "system/dept/dept";
    }
    /**
     * 跳转到菜单管理(左边)
     * @return
     */
    @GetMapping("/toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理（右边）
     * @return
     */
    @GetMapping("/toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }

    /**
     * 跳转到菜单管理
     * @return
     */
    @GetMapping("/menu")
    public String menu(){
        return "system/menu/menu";
    }

    /**
     * 跳转到在线用户管理
     * @return
     */
    @GetMapping("/online")
    public String online(){
        return "system/monitor/online";
    }

    /**
     * 跳转到系统日志管理
     * @return
     */
    @GetMapping("/systemLog")
    public String systemLog(){
        return "system/monitor/systemLog";
    }
    /**
     * 跳转到角色管理页面
     * @return
     */
    @GetMapping("/role")
    public String role(){
        return "system/role/role";
    }
    /**
     * 跳转到druid监控
     * @return
     */
    @GetMapping("/druid")
    public String druid(){
        return "redirect:/druid";
    }

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
