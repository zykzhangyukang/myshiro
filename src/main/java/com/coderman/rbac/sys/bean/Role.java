package com.coderman.rent.sys.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "sys_role")
@Data
public class Role {
    @Id
    private Long id;

    private String roleName;

    private String remark;

    private Date createTime;

    private Date modifiedTime;

}