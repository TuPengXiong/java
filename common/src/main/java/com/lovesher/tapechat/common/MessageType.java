package com.lovesher.tapechat.common;

/**
 * Created by tpx on 2017/10/7.
 */
public enum MessageType {

    MESSAGE(0,"文本消息"),
    IMAGE(1,"图片消息"),
    FILE(2,"文件消息");
    private int type;

    private String desp;

    MessageType(int type, String desp) {
        this.type = type;
        this.desp = desp;
    }

}
