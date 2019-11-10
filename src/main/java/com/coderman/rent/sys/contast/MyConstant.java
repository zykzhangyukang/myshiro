package com.coderman.rent.sys.contast;

/**
 * Created by zhangyukang on 2019/11/10 12:07
 */
public interface MyConstant {
    /**
     * session中User
     */
    String USER="user";

    /**
     * 菜单的可用状态(0不可用，1可用)
     */
     Integer AVAILABLE=1;
    Integer UN_AVAILABLE=0;

    /**
     * 菜单的展开状态(0不展开，1：展开)
     */
    Integer MENU_OPEN=1;
    Integer MENU_CLOSE=0;
    /**
     * 菜单的类型（0，菜单，1：按钮）
     */
    Integer MENU_TYPE = 0;
    Integer BTN_TYPE = 1;
}
