
package com.coderman.rbac.base.controller;

import com.coderman.rbac.base.dto.AccessTokenDTO;
import com.coderman.rbac.base.dto.GithubUser;
import com.coderman.rbac.base.provider.GithubProvider;
import com.coderman.rbac.sys.bean.ActiveUser;
import com.coderman.rbac.sys.bean.LoginLog;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.controller.LogController;
import com.coderman.rbac.sys.controller.LoginController;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.enums.UserTypeEnum;
import com.coderman.rbac.sys.service.LogService;
import com.coderman.rbac.sys.service.LoginLogService;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.MD5Util;
import com.coderman.rbac.sys.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.UUID;

@Api(value = "Github第三方登入模块")
@Controller
public class AuthorizeController {

    protected static final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String github_clientId;

    @Value("${github.client.secret}")
    private String github_clientSecret;

    @Value("${github.client.redirecturi}")
    private String github_RedirectUri;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserService userService;

    /**
     * github登入回调
     *
     * @param code
     * @param
     * @return
     */
    @ApiOperation(value = "Github的回调",notes = "github登入回调")
    @GetMapping("/githubCallBack")
    public String githubCallBack(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(github_clientId);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(github_clientSecret);
        accessTokenDTO.setRedirect_uri(github_RedirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setUserName(githubUser.getName());
            user.setAvatar(githubUser.getAvatar_url());
            user.setDescription(githubUser.getBio());
            userService.saveOrUpdate(user);
            //shiro授权
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUserName(),MyConstant.DEFAULT_PWD);
            try {
                subject.login(usernamePasswordToken);
                logger.info("【第三方用户登入成功】");
                WebUtil.getSession().setAttribute(MyConstant.USER,user);
                //记录登入日志
                ActiveUser activeUser= (ActiveUser) subject.getPrincipal();
                LoginLog loginLog = LoginController.createLoginLog(activeUser);
                loginLogService.saveLog(loginLog);
                return "redirect:/system/index";
            } catch (AuthenticationException e) {
                e.printStackTrace();
                logger.error("【第三方用户登入失败】");
            }
            return "redirect:/";
        } else {
            //登入失败
            logger.error("用户登入失败:{}", githubUser);
            return "redirect:/";
        }
    }
}

