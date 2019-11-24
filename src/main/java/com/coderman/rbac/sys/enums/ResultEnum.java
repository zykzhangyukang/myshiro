package com.coderman.rbac.sys.enums;

/**
 * Created by zhangyukang on 2019/11/10 10:22
 */
public enum ResultEnum {

    OK(0,"请求成功"),
    ERROR(1,"请求失败"),
    NAME_OR_PWD_EMPTY(2,"用户名或密码必填"),
    PWD_ERROR(3,"密码错误" ),
    DELETE_SUCCESS(4,"删除成功"),
    DELETE_FAIL(5,"删除失败"),
    UPDATE_SUCCESS(6,"更新成功"),
    UPDATE_FAIL(7,"更新失败"),
    ADD_SUCCESS(8,"添加成功"),
    ADD_FAIL(9,"添加失败"),
    SQ_SUCCESS(8,"授权成功"),
    SQ_FAIL(9,"授权失败"),
    KICK_SUCCESS(10,"踢出成功" ),
    KICK_FAIL(10,"踢出失败" ),
    GIVE_ROLE_SUCCESS(11,"分配角色成功" ),
    GIVE_ROLE_FAIL(12,"分配角色失败" ),
    YOU_NOT_PERMISSION(13,"悲剧了~，权限不足" ),
    LOCK_SUCCESS(14,"成功锁定" ),
    LOCK_FAIL(14,"锁定失败" ),
    SYS_ERROR(15,"系统异常" ),
    RESET_PWD_SUCCESS(16,"重置密码成功" ),
    RESET_PWD_FAIL(16,"重置密码失败" ),
    PUBLISH_MESSAGE_SUCCESS(17,"留言成功" ),
    PUBLISH_MESSAGE_FAIL(18,"留言失败" ),
    UPLOAD_IMAGE_SUCCESS(19, "图片上传成功"),
    UPLOAD_IMAGE_FAIL(20, "图片上传失败"),
    LOGIN_SUCCESS(21, "登入成功"),
    FLUSH_SUCCESS(22,"刷新成功" ),
    FLUSH_ERROR(23,"刷新失败" );
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
