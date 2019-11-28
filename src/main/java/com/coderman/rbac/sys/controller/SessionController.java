package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.bean.ActiveUser;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.SessionService;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.UserVo;
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

    @Autowired
    private UserService userService;

    @GetMapping("/flush")
    public ResultVo flush(UserVo userVo){
        User userById = userService.findUserById(userVo);
        if(userById!=null){
            try {
                WebUtil.getSession().setAttribute(MyConstant.USER,userById);
                return ResultVo.OK(ResultEnum.FLUSH_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVo.ERROR(ResultEnum.FLUSH_ERROR);
            }
        }else {
            return ResultVo.ERROR(ResultEnum.FLUSH_ERROR);
        }
    }

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
    @RequiresPermissions({"session:user:kickout"})
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
