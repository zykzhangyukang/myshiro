package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.Menu;
import com.coderman.rent.sys.bean.MenuNode;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.enums.UserTypeEnum;
import com.coderman.rent.sys.service.MenuService;
import com.coderman.rent.sys.utils.MenuTreeBuilder;
import com.coderman.rent.sys.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyukang on 2019/11/10 11:47
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 加载系统左边的菜单
     * @return
     */
    @GetMapping("/loadLeftMenu")
    public List<MenuNode> loadLeftMenu(){
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        List<MenuNode> menuNodeList=new ArrayList<>();
        if(user.getType().equals(UserTypeEnum.SYSTEM_USER.getCode())){
            //超级管理员
            List<Menu> menus = menuService.loadAllMenu();
            if(!CollectionUtils.isEmpty(menus)){
                for (Menu menu : menus) {
                    Boolean spread=menu.getIsOpen()==MyConstant.MENU_OPEN? true:false;
                    Long parentId = menu.getParentId();
                    MenuNode menuNode = new MenuNode(menu.getMenuId(),menu.getMenuName(), menu.getIcon(), menu.getUrl(), spread, parentId);
                    menuNodeList.add(menuNode);
                }
            }
        }else {
            //普通用户(根据用户的角色查询权限获取用户的菜单)

        }
        List<MenuNode> MenuTree= MenuTreeBuilder.build(menuNodeList);
        return MenuTree;
    }

}
