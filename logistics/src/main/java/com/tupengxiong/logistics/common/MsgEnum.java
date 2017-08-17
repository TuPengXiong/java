package com.tupengxiong.logistics.common;

/**
 * Created by tpx on 2017/7/22.
 */
public enum MsgEnum {
    SUCCESS(0, "成功"),
    NOT_LOGIN_ERROR(401, "尚未登录或权限不足"),
    NOT_ACCESS_ERROR(403, "权限不足"),
    USERNAME_NOT_FOUND_ERROR(-2, "用户名或密码错误!"),
    NOT_FOUND_ERROR(404, "资源不存在"),
    Fail(-1, "失败");

    private int code;

    private String msg;

    MsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
