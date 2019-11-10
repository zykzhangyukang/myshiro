package com.coderman.rent.sys.realm;

import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证
 * Created by zhangyukang on 2019/11/9 19:58
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.findUserByName(username);
        if(null!=user){
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            Object hashedCredentials=user.getPassWord();
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            return new SimpleAuthenticationInfo(activeUser,  hashedCredentials,  credentialsSalt, getName());
        }
        return null;
    }
}
