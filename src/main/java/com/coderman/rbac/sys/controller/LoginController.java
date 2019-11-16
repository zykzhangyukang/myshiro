package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.bean.LoginLog;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.service.LoginLogService;
import com.coderman.rent.sys.service.UserService;
import com.coderman.rent.sys.utils.AddressUtil;
import com.coderman.rent.sys.utils.IPUtil;
import com.coderman.rent.sys.utils.BrowserUtil;
import com.coderman.rent.sys.utils.WebUtil;
import com.coderman.rent.sys.vo.LoginLogVo;
import com.coderman.rent.sys.vo.PageVo;
import com.coderman.rent.sys.vo.ResultVo;
import com.coderman.rent.sys.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.List;

/**
 * 用户登入控制器
 * Created by zhangyukang on 2019/11/10 10:06
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserService userService;
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
            WebUtil.getSession().setAttribute(MyConstant.USER,activeUser.getUser());
            //记录登入日志
            HttpServletRequest request = WebUtil.getRequest();
            LoginLog loginLog=new LoginLog();
            loginLog.setUserName(activeUser.getUser().getUserName());
            loginLog.setIp(IPUtil.getIpAddr(request));
            loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
            loginLog.setSystem(System.getProperty("os.name"));
            loginLog.setBrowser(BrowserUtil.getRequestBrowserInfo(request));
            loginLog.setLoginTime(new Date());
            loginLogService.saveLog(loginLog);
            //更新最新登入时间
            userService.updateLastLoginTime(activeUser);

            log.info("【登入成功】 user={}",WebUtil.getSession().getAttribute(MyConstant.USER));
            return ResultVo.OK();
        }catch (IncorrectCredentialsException e){
            log.error("【登入失败】 message={}",e.getMessage());
            return ResultVo.ERROR(ResultEnum.PWD_ERROR);
        } catch (AuthenticationException e) {
            log.error("【登入失败】 message={}",e.getMessage());
            return ResultVo.ERROR(e.getMessage());
        }
    }

    /**
     * 批量删除登入日志
     * @param loginLogVo
     * @return
     */
    @RequiresPermissions({"login:batchDeleteLog"})
    @GetMapping("/batchDelete")
    public ResultVo batchDelete(LoginLogVo loginLogVo){
        try {
            loginLogService.batchDelete(loginLogVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }

    /**
     * 删除登入日志
     * @return
     */
    @RequiresPermissions({"login:deleteLog"})
    @GetMapping("/deleteLog")
    public ResultVo deleteLog(LoginLogVo loginLogVo){
        try {
            loginLogService.deleteLog(loginLogVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResultVo.OK(ResultEnum.DELETE_FAIL);
        }
    }
    /**
     * 登入日志
     * @param loginLogVo
     * @return
     */
    @GetMapping("/logList")
    public PageVo<LoginLog> logList(LoginLogVo loginLogVo){
        PageVo<LoginLog> page=loginLogService.findPage(loginLogVo);
        return page;
    }

}
