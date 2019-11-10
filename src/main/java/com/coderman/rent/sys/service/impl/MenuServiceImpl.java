package com.coderman.rent.sys.service.impl;

import com.coderman.rent.sys.bean.Menu;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.mapper.MenuMapper;
import com.coderman.rent.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/10 11:54
 */
@Service
public class MenuServiceImpl implements MenuService {

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
}
