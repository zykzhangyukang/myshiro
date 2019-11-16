package com.coderman.rbac.sys.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Table(name = "sys_login_log")
public class LoginLog {
    @Id
    private Long id;

    private String userName;

    private Date loginTime;

    private String location;

    private String ip;

    private String system;

    private String browser;


}