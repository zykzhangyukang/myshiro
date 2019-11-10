package com.coderman.rent.sys.enums;

/**
 * Created by zhangyukang on 2019/11/10 10:22
 */
public enum ResultEnum {

    OK(0,"请求成功"),
    ERROR(1,"请求失败"),
    NAME_OR_PWD_EMPTY(2,"用户名或密码必填"),
    LOGIN_FAIL(3,"用户名或密码错误" );
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
