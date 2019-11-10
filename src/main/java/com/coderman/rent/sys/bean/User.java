package com.coderman.rent.sys.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统用户
 */
@Data
@Table(name = "sys_user")
public class User {
    /**用户id 主键**/
    @Id
    private Long userId;

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

    /**用户状态:0锁定  1：有效**/
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

}