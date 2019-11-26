package com.coderman.rbac.sys.service;

import com.coderman.rbac.sys.bean.LoginLog;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.vo.LoginLogVo;
import com.coderman.rbac.sys.vo.PageVo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/10 15:06
 */
public interface LoginLogService {
    /**
     * 查询登入日志
     * @param loginLogVo
     * @return
     */
    PageVo<LoginLog> findPage(LoginLogVo loginLogVo);

    /**
     * 用户登入日志
     * @param loginLog
     */
    void saveLog(LoginLog loginLog);

    /**
     * 删除登入日志
     * @param loginLogVo
     */
    void deleteLog(LoginLogVo loginLogVo);

    /**
     * 批量删除
     * @param loginLogVo
     */
    void batchDelete(LoginLogVo loginLogVo);

    /**
     * 登入7天日志统计
     * @param user
     * @return
     */
    List<Map<String,Object>> findLastSevenDaysVisitCount(User user);

    /**
     * 登入位置统计
     * @return
     */
    List<Map<String,Object>> loadLoginLocation();

}
