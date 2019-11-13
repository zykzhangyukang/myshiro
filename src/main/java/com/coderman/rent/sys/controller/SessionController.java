package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.service.SessionService;
import com.coderman.rent.sys.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/12 12:15
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * 获取当前在线人数
     * @param username
     * @return
     */
    @GetMapping("/online/{username}")
    public ResultVo<List<ActiveUser>> online(@PathVariable(value = "username") String username){
        List<ActiveUser> activeUsers=sessionService.listOnLine(username);
        return ResultVo.OK(activeUsers);
    }

    /**
     * 踢出用户
     * @param id
     * @return
     */
    @RequiresPermissions({"user:kickout"})
    @GetMapping("delete/{id}")
    public ResultVo forceLogout(@PathVariable String id) {
        try {
            sessionService.forceLogout(id);
            return ResultVo.OK(ResultEnum.KICK_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.KICK_FAIL);
        }

    }

}
