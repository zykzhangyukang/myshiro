package com.coderman.rbac.sys.enums;

/**
 * 用户类型
 * Created by zhangyukang on 2019/11/10 12:04
 */
public enum  UserTypeEnum {

    SYSTEM_USER(0,"系统用户"),
    COMMON_USER(1,"普通用户"), GITHUB_USER(2,"第三方Github用户");

    UserTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
