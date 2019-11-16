package com.coderman.rbac.sys.realm;

import com.coderman.rbac.sys.bean.ActiveUser;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.service.MenuService;
import com.coderman.rbac.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证
 * Created by zhangyukang on 2019/11/9 19:58
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ActiveUser activeUser=(ActiveUser) principalCollection.getPrimaryPrincipal();
        User user=activeUser.getUser();
        List<String> permissions = activeUser.getPermissions();
        if(user.getType()==0) {
            authorizationInfo.addStringPermission("*:*");
        }else {
            if(null!=permissions&&permissions.size()>0) {
                authorizationInfo.setRoles(new HashSet<>( activeUser.getRoles()));
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
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
        if(null==user){
            throw new UnknownAccountException("该账号不存在");
        } else {
            if(user.getStatus().equals(MyConstant.LOCKED)){
                throw new LockedAccountException("用户账号已被锁定");
            }
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            Object hashedCredentials=user.getPassWord();
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            return new SimpleAuthenticationInfo(activeUser,  hashedCredentials,  credentialsSalt, getName());
        }
    }
}
