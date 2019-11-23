package com.coderman.rbac.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name ="sys_dept")
public class Department {
    @Id
    private Long id;

    private Long parentId;

    private String deptName;

    private Long orderNumber;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date createTime;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date modifiedTime;

    private String remark;

    private String phone;


    private String address;

    private Integer isOpen;

}