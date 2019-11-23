package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.annotation.ControllerEndpoint;
import com.coderman.rbac.sys.bean.*;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.enums.UserTypeEnum;
import com.coderman.rbac.sys.service.MenuService;
import com.coderman.rbac.sys.service.RoleService;
import com.coderman.rbac.sys.utils.MenuTreeBuilder;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.MenuVo;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.RoleVo;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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

    @Autowired
    private RoleService roleService;


    /**
     * 删除菜单
     * @return
     */
    @RequiresPermissions({"menu:delete"})
    @PostMapping("/delete")
    @ControllerEndpoint(exceptionMessage = "删除菜单失败",operation ="删除菜单/按钮")
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
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    @ControllerEndpoint(exceptionMessage = "添加菜单失败",operation ="添加菜单/按钮")
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
    @RequiresPermissions({"menu:update"})
    @PostMapping("/update")
    @ControllerEndpoint(exceptionMessage = "更新菜单失败",operation ="更新菜单/按钮")
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
            menuListToMenuNodeList(menuNodeList, menus);
        }else if (user.getType().equals(UserTypeEnum.COMMON_USER)){
            //普通用户(根据用户的角色查询权限获取用户的菜单)
            List<Menu> menus=menuService.loadAllMenuByUserId(user.getId());
            menuListToMenuNodeList(menuNodeList, menus);
            //设置普通用户的角色，和权限编码
            Subject subject = SecurityUtils.getSubject();
            ActiveUser activeUser= (ActiveUser) subject.getPrincipal();
            activeUser.setRoles(roleService.findRoleNameByUserId(user.getId()));
            activeUser.setPermissions(menuService.findUserPermissionCode(user.getId()));
        }
        List<MenuNode> MenuTree= MenuTreeBuilder.build(menuNodeList);
        return MenuTree;
    }

    /**
     * 将菜单转化成菜单节点
     * @param menuNodeList
     * @param menus
     */
    private void menuListToMenuNodeList(List<MenuNode> menuNodeList, List<Menu> menus) {
        if(!CollectionUtils.isEmpty(menus)){
            for (Menu menu : menus) {
                Boolean spread=menu.getIsOpen()== MyConstant.MENU_OPEN? true:false;
                Long parentId = menu.getParentId();
                MenuNode menuNode = new MenuNode(menu.getId(),menu.getMenuName(), menu.getIcon(), menu.getUrl(), spread, parentId);
                menuNodeList.add(menuNode);
            }
        }
    }

}
