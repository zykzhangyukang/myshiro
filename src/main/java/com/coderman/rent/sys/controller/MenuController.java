package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.MenuDTreeJson;
import com.coderman.rent.sys.bean.Menu;
import com.coderman.rent.sys.bean.MenuNode;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.enums.UserTypeEnum;
import com.coderman.rent.sys.service.MenuService;
import com.coderman.rent.sys.utils.MenuTreeBuilder;
import com.coderman.rent.sys.utils.WebUtil;
import com.coderman.rent.sys.vo.MenuVo;
import com.coderman.rent.sys.vo.PageVo;
import com.coderman.rent.sys.vo.ResultVo;
import com.coderman.rent.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * 删除菜单
     * @return
     */
    @PostMapping("/delete")
    public ResultVo delete(MenuVo menuVo){
        try {
            if( menuVo.getId()!=null)
            menuService.delete(menuVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }

    /**
     * 添加菜单
     * @return
     */
    @PostMapping("/add")
    public ResultVo add(MenuVo menuVo){
        try {
            menuService.add(menuVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.ADD_FAIL);
        }
    }

    /**
     * 查询所有的菜单
     * @return
     */
    @GetMapping("/findMenuPage")
    public PageVo<Menu> findMenuPage(MenuVo menuVo){
        PageVo<Menu> menus=menuService.findMenuPage(menuVo);
        return menus;
    }

    /**
     * 更新菜单
     * @return
     */
    @PostMapping("/update")
    public ResultVo update(MenuVo menuVo){
        try {
            Long id = menuVo.getId();
            if(id!=null)
            menuService.update(menuVo);
            return ResultVo.OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.UPDATE_FAIL);
        }

    }

    /**
     * 根据角色id获取该角色菜单树
     * @return
     */
    @PostMapping("/loadAllMenuByRoleId")
    public ResultVo<List<MenuDTreeJson>> loadAllMenuByRoleId(RoleVo roleVo){
        List<MenuDTreeJson> menuDTreeJsons = menuService.loadAllMenuByRoleId(roleVo.getId());
        return ResultVo.OK(menuDTreeJsons);
    }
    /**
     * 所有可用的菜单和按钮
     * @return: 角色添加页面JSON（dTree）
     */
    @PostMapping("/loadAllMenuJSON")
    public ResultVo<List<MenuDTreeJson>> loadAllMenuJSON(){
        List<MenuDTreeJson> menuDTreeJsons = menuService.loadAllMenuJSON();
        return ResultVo.OK(menuDTreeJsons);
    }

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
                    MenuNode menuNode = new MenuNode(menu.getId(),menu.getMenuName(), menu.getIcon(), menu.getUrl(), spread, parentId);
                    menuNodeList.add(menuNode);
                }
            }
        }else {
            //普通用户(根据用户的角色查询权限获取用户的菜单)
           //TODO
        }
        List<MenuNode> MenuTree= MenuTreeBuilder.build(menuNodeList);
        return MenuTree;
    }

}
