package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.exception.SysException;
import com.coderman.rent.sys.utils.WebUtil;
import com.coderman.rent.sys.vo.ResultVo;
import com.coderman.rent.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登入控制器
 * Created by zhangyukang on 2019/11/10 10:06
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 用户登入
     * @return
     */
    @PostMapping("/login")
    public ResultVo login(UserVo userVo){
        if(StringUtils.isEmpty(userVo.getPassWord())||StringUtils.isEmpty(userVo.getPassWord())){
            return ResultVo.ERROR(ResultEnum.NAME_OR_PWD_EMPTY);
        }
        UsernamePasswordToken token=new UsernamePasswordToken(userVo.getUserName(),userVo.getPassWord());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            ActiveUser activeUser= (ActiveUser) subject.getPrincipal();
            //把用户放到session中
            WebUtil.getSession().setAttribute("user",activeUser.getUser());
            return ResultVo.OK();
        } catch (AuthenticationException e) {
            return ResultVo.ERROR(ResultEnum.LOGIN_FAIL);
        }
    }
}
