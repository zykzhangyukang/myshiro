package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.LoginLog;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/10 15:08
 */
@Data
public class LoginLogVo extends LoginLog {
    private int page;
    private int limit;
    private String range;
    private String ids;
}
