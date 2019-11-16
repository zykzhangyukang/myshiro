package com.coderman.rbac.sys.service;

import com.coderman.rbac.sys.bean.MenuDTreeJson;
import com.coderman.rbac.sys.bean.Menu;
import com.coderman.rbac.sys.vo.MenuVo;
import com.coderman.rbac.sys.vo.PageVo;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/10 11:49
 */
public interface MenuService {
    /**
     * 查询所有的可用菜单
     * @return
     */
    public List<Menu> loadAllMenu();

    /**
     * 根据用户id查询菜单
     * @param userId
     * @return
     */
    public List<Menu> loadAllMenuByUserId( Long userId);

    /**
     * 加载所有可用的菜单（dTree）
     * @return
     */
    List<MenuDTreeJson> loadAllMenuJSON();

    /**
     * 根据角色Id加载所有可用的菜单（dTree）
     * @param id：角色id
     * @return
     */
    List<MenuDTreeJson> loadAllMenuByRoleId(Long id);

    /**
     * 查询所有的菜单
     * @param menuVo
     * @return
     */
    PageVo<Menu> findMenuPage(MenuVo menuVo);

    /**
     * 更新菜单信息
     * @param menuVo
     */
    void update(MenuVo menuVo);

    /**
     * 添加菜单信息
     * @param menuVo
     */
    void add(MenuVo menuVo);

    /**
     * 删除菜单
     * @param menuVo
     */
    void delete(MenuVo menuVo);

    /**
     * 查询用户具有的按钮权限
     * @param id
     * @return
     */
    List<String> findUserPermissionCode(Long id);
}
