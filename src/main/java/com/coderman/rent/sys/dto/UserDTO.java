package com.coderman.rent.sys.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangyukang on 2019/11/11 15:38
 */
@Data
public class UserDTO {

    private Long id;

    /**用户名**/
    private String userName;

    /**密码**/
    private String passWord;

    /**部门**/
    private Long deptId;

    /**邮箱**/
    private String email;

    /**电话号码**/
    private String phoneNumber;

    /**用户状态:0锁定  1：有效,默认正常**/
    private String status;

    /**用户创建时间**/
    private Date createTime;

    /**用户修改时间**/
    private Date modifiedTime;

    /**最新登入时间**/
    private Date lastLoginTime;

    /**性别**/
    private String sex;

    /**头像**/
    private String avatar;

    /**用户描述**/
    private String description;

    /**盐**/
    private String salt;

    /**用户类型 0:超级管理员，1：系统普通用户，默认为普通用户**/
    private Integer type;

    /**用户所在部门**/
    private String deptName;
}
