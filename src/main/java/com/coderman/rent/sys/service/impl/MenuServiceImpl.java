package com.coderman.rent.sys.service.impl;

import com.coderman.rent.sys.bean.DTreeJson;
import com.coderman.rent.sys.bean.Menu;
import com.coderman.rent.sys.bean.RoleMenu;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.mapper.MenuMapper;
import com.coderman.rent.sys.mapper.RoleMapper;
import com.coderman.rent.sys.mapper.RoleMenuMapper;
import com.coderman.rent.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangyukang on 2019/11/10 11:54
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> loadAllMenu() {
        Example example=new Example(Menu.class);
        example.createCriteria().andEqualTo("available", MyConstant.AVAILABLE)
                                .andEqualTo("type",MyConstant.MENU_TYPE);
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    }

    @Override
    public List<Menu> loadAllMenuByUserId(Long userId) {
        return null;
    }

    @Override
    public List<DTreeJson> loadAllMenuJSON() {
        Example o = new Example(Menu.class);
        o.createCriteria().andEqualTo("available",MyConstant.AVAILABLE);
        List<Menu> menus = menuMapper.selectByExample(o);
        List<DTreeJson> list=new ArrayList<>();
        if(!CollectionUtils.isEmpty(menus)){

            for (Menu menu : menus) {
                Boolean spread=menu.getIsOpen()==MyConstant.MENU_OPEN? true:false;
                list.add(new DTreeJson(menu.getMenuId(),menu.getMenuName(),spread,menu.getParentId(),"0"));
            }
        }
        return list;
    }

    @Override
    public List<DTreeJson> loadAllMenuByRoleId(Long id) {
        List<DTreeJson> list=new ArrayList<>();
        //查询该角色的所有的菜单Id
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectByExample(o);
        Set<Long> mIdList=new HashSet<>();
        //根据菜单查出菜单
        if(!CollectionUtils.isEmpty(roleMenus)){
            for (RoleMenu roleMenu : roleMenus) {
                mIdList.add(roleMenu.getMenuId());
            }
        }
        //查询所有可用菜单
        Example o1 = new Example(Menu.class);
        o1.createCriteria().andEqualTo("available",MyConstant.AVAILABLE);
        List<Menu> allMenus = menuMapper.selectByExample(o1);
        //封装返回的JSON
        for (Menu menu : allMenus) {
            String checkArr="0";
            if(mIdList.contains(menu.getMenuId())){
                checkArr="1";
            }
            Boolean spread=menu.getIsOpen()==MyConstant.MENU_OPEN? true:false;
            list.add(new DTreeJson(menu.getMenuId(),menu.getMenuName(),spread,menu.getParentId(),checkArr));
        }
        return list;
    }
}
