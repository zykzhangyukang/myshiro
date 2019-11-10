package com.coderman.rent.sys.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 菜单
 */
@Table(name = "sys_menu")
@Data
public class Menu {
    @Id
    private Long menuId;

    private Long parentId;

    private String menuName;

    private String url;

    private String icon;

    private String type;

    private Long orderNum;

    private Date createTime;

    private Date modifiedTime;

    private String perms;

    private Integer available;

    private Integer isOpen;
}