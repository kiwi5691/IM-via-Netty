package com.IM.netty.utils;

public enum  ResponseCode {

    ZEROFIRENDS(222, "您还没有私信"),
    UNSECURITYVERIFY(403, "没有权限"),
    ZEROMSGS(200, "您们没有聊天记录");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }
}
