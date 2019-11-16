package com.coderman.rbac.sys.utils;

import com.coderman.rbac.sys.bean.MenuNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 构造菜单树
 * Created by zhangyukang on 2019/11/10 12:36
 */
public class MenuTreeBuilder {

    /**
     * 构建系统右侧的菜单树
     * @param menuNodeList
     * @return
     */
    public static List<MenuNode> build(List<MenuNode> menuNodeList) {
        List<MenuNode> tree=new ArrayList<>();
        for (MenuNode n1 : menuNodeList) {
            Long pId = n1.getPId();
            if(pId== 0){
                tree.add(n1);
            }
            for (MenuNode n2 : menuNodeList) {
                //BUG:比较的时候不能使用==
                if(n2.getPId().equals(n1.getMenuId())){
                   n1.getChildren().add(n2);
                }
            }
        }
        return tree;
    }
}
