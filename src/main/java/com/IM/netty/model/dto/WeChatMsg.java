package com.IM.netty.model.dto;

import com.IM.netty.entity.UserMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data

public class WeChatMsg {
    private Long id;

    private Integer sendId;

    private Integer acceptId;

    private String msg;

    private Integer isSign;

    //对应枚举类MsgTypeEnum
    private String type;

    public WeChatMsg(){

    }
    public WeChatMsg(UserMsg userMsg) {
        //todo 构造
        this.id = id;
        this.sendId = sendId;
        this.acceptId = acceptId;
        this.msg = msg;
        this.isSign = isSign;
        this.type = type;
    }
}
