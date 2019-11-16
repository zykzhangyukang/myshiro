package com.coderman.rbac.sys.service;

import com.coderman.rbac.sys.bean.LoginLog;
import com.coderman.rbac.sys.vo.LoginLogVo;
import com.coderman.rbac.sys.vo.PageVo;

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
}
