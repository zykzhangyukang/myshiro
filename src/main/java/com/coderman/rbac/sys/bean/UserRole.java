package com.coderman.rbac.sys.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * 角色用户关联表
 */
@Data
@Table(name = "sys_user_role")
public class UserRole {

    private Long userId;

    private Long roleId;

}