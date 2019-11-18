package com.coderman.rbac.sys.service;

import com.coderman.rbac.sys.bean.ActiveUser;
import com.coderman.rbac.sys.bean.Role;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.dto.UserDTO;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/10 10:44
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    User findUserByName(String userName);


    /**
     * 查询用户
     * @param userVo
     * @return
     */
    PageVo<UserDTO> findPage(UserVo userVo);

    /**
     * 用户更新
     * @param userVo
     */
    void update(UserVo userVo);

    /**
     * 添加用户信息
     * @param userVo
     */
    void add(UserVo userVo);

    /**
     * 删除用户
     * @param userVo
     */
    void delete(UserVo userVo);

    /**
     * 批量删除
     * @param userVo
     */
    void batchDelete(UserVo userVo);

    /**
     * 更新用户的最新登入时间
     * @param activeUser
     */
    void updateLastLoginTime(ActiveUser activeUser);

    /**
     * 根据用户的id获取拥有的角色
     * @param userVo
     */
    List<Role> listRolesByUserId(UserVo userVo);

    /**
     * 加载用户拥有的角色和没有拥有的角色
     * @param userVo
     * @return
     */
    Map<String,Object> loadUserRoles(UserVo userVo);

    /**
     * 给用户分配权限
     * @param userVo
     * @param integers
     */
    void giveUserRoles(UserVo userVo, List<Integer> integers);

    /**
     * 批量重置用户的密码
     * @param userVo
     */
    void reSetUser(UserVo userVo);
}
