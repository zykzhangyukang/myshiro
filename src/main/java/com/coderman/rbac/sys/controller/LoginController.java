package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.bean.ActiveUser;
import com.coderman.rbac.sys.bean.LoginLog;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.LoginLogService;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.AddressUtil;
import com.coderman.rbac.sys.utils.BrowserUtil;
import com.coderman.rbac.sys.utils.IPUtil;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.LoginLogVo;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.UserVo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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
import java.util.Date;

/**
 * 用户登入控制器
 * Created by zhangyukang on 2019/11/10 10:06
 */
@Api(value = "用户登入模块")
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
    @ApiOperation(value = "用户登入",notes = "用户登入系统")
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
            WebUtil.getSession().setAttribute("ActiveUser",activeUser);
            //记录登入日志
            LoginLog loginLog = createLoginLog(activeUser);
            loginLogService.saveLog(loginLog);
            //更新最新登入时间
            userService.updateLastLoginTime(activeUser);
            log.info("【登入成功】 user={}",WebUtil.getSession().getAttribute(MyConstant.USER));
            return ResultVo.OK(ResultEnum.LOGIN_SUCCESS);
        }catch (IncorrectCredentialsException e){
            log.error("【登入失败】 message={}",e.getMessage());
            return ResultVo.ERROR(ResultEnum.PWD_ERROR);
        } catch (AuthenticationException e) {
            log.error("【登入失败】 message={}",e.getMessage());
            return ResultVo.ERROR(e.getMessage());
        }
    }

    /**
     * 创建登入日志
     * @param activeUser
     * @return
     */
    public static LoginLog createLoginLog(ActiveUser activeUser) {
        HttpServletRequest request = WebUtil.getRequest();
        LoginLog loginLog=new LoginLog();
        loginLog.setUserName(activeUser.getUser().getUserName());
        loginLog.setIp(IPUtil.getIpAddr(request));
        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        // 获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setSystem(os.getName());
        loginLog.setBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }

    /**
     * 批量删除登入日志
     * @param loginLogVo
     * @return
     */
    @ApiOperation(value = "批量删除登入日志",notes = "批量删除用户登入日志")
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
    @ApiOperation(value = "删除登入日志",notes = "删除单条登入日志")
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
    @ApiOperation(value = "查询登入日志",notes = "查询系统的登入日志分页")
    @GetMapping("/logList")
    public PageVo<LoginLog> logList(LoginLogVo loginLogVo){
        PageVo<LoginLog> page=loginLogService.findPage(loginLogVo);
        return page;
    }

}
