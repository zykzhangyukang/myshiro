package com.coderman.rbac.sys.service;

import com.coderman.rbac.sys.bean.ActiveUser;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/12 12:16
 */
public interface SessionService {
    /**
     * 当前在线人数
     * @return
     */
    List<ActiveUser> listOnLine(String username);

    /**
     * 踢出用户
     * @param sessionId
     */
    void forceLogout(String sessionId);

}
