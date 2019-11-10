package com.coderman.rent.sys.service;

import com.coderman.rent.sys.bean.Menu;

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

    public List<Menu> loadAllMenuByUserId( Long userId);

}
