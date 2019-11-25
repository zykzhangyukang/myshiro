package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhangyukang on 2019/11/10 10:17
 */
@Data
public class UserVo extends User{
    private int page;
    private int limit;
    private String range;
    private String ids;

    /**父级部门Id**/
    private Long parentDeptId;

    private String deptName;

    private String birth;

    /**新密码**/
    private String newPassWord;

    /**旧密码**/
    private String oldPassWord;

}
