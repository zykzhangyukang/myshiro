package com.coderman.rbac.sys.contast;

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


    /**
     * 第三方电影接口
     */
    public  static  final  String HOT_MOVIE_URL = "https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=328";

   /**
     * 异步线程池名称
     */
    public static final String ASYNC_POOL = "RBACAsyncThreadPool";
    /**
     * 第三方用户角色
     */
    public static final String GITHUB_ROLE = "GitHub用户";
    /**
     * 用户默认头像
     */
    public static final String DEFAULT_AVATAR = "/resources/images/face.jpg";
    /**
     * 请假流程相关
     */
    public static final Integer SICK_STATUS_APPLY = 1;//提交申请
    public static final Integer SICK_STATUS_DEALING = 2;//申请中
    public static final Integer SICK_STATUS_PASS = 3;//申请通过
    public static final Integer SICK_STATUS_UNPASS = 4;//申请不通过
    public static final Integer SICK_STATUS_QUIT = 5;//放弃申请
    public static final String PROCESS_KEY = "员工请假流程";
    public static final String STRING_QUIT = "申请人放弃";
    public static final String STRING_SICK_PAPER_ID = "sickPaperId";
}
