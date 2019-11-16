package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.User;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/10 10:17
 */
@Data
public class UserVo extends User{
    private int page;
    private int limit;
    private String range;
    private String ids;

    private String deptName;
}
