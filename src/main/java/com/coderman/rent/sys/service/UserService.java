package com.coderman.rent.sys.service;

import com.coderman.rent.sys.bean.User;

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

}
