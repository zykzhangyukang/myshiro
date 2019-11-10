package com.coderman.rent.sys.contast;

/**
 * Created by zhangyukang on 2019/11/10 12:07
 */
public class MyConstant {


    /**
     * 系统默认密码
     */
    public static final String DEFAULT_PWD="zhangyukang";
    /**
     * 用户锁定状态
     */
    public static final String LOCKED="0";
    public static final String UN_LOCKED="1";

    /**
     * 用户性别默认为男
     */
    public static final String SEX_MAN="0";
    public static final String SEX_WOMEN="1";

    /**
     * 时间范围
     */
    public static final String START_TIME="start";
    public static final String END_TIME="end";

    /**
     * 排序方式
     */
    public static final String ORDER_DESC="desc";
    public static final String ORDER_ASC="asc";

    /**
     * session中User
     */
    public  static  final  String USER="user";

    /**
     * 菜单的可用状态(0不可用，1可用)
     */
    public  static  final  Integer AVAILABLE=1;
    public  static  final Integer UN_AVAILABLE=0;

    /**
     * 菜单的展开状态(0不展开，1：展开)
     */
    public  static  final  Integer MENU_OPEN=1;
    public  static  final  Integer MENU_CLOSE=0;
    /**
     * 菜单的类型（0，菜单，1：按钮）
     */
    public  static  final  Integer MENU_TYPE = 0;
    public  static  final  Integer BUTTON_TYPE = 1;

}
