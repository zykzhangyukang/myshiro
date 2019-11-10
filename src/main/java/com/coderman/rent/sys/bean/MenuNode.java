package com.coderman.rent.sys.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树JSON
 * Created by zhangyukang on 2019/11/10 12:21
 */
@Data
@NoArgsConstructor
public class MenuNode {

    private Long menuId;

    private String title;

    private String icon;

    private String href;

    private Boolean spread;

    private Long pId;

    private List<MenuNode> children=new ArrayList<>();

    private String target;

    /**
     * 系统右侧菜单的构造器
     * @param title
     * @param icon
     * @param href
     * @param spread
     */
    public MenuNode(Long menuId,String title, String icon, String href, Boolean spread,Long pId) {
        this.menuId=menuId;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
        this.children = children;
        this.pId=pId;
    }
    public MenuNode(String title, String icon, String href, Boolean spread) {
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }


}
