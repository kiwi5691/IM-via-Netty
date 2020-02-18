package com.IM.netty.model.dto;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.utils.TypeChecksUtils;
import com.IM.netty.utils.pacDTOs;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Date;

import static com.IM.netty.utils.pacDTOs.pacTimeString;

@Data

public class WeChatMsg {
    private String id;

    private Integer sendId;

    private Integer acceptId;

    private String msg;

    private Integer isSign;

    //对应枚举类MsgTypeEnum
    private String type;

    private String date;

    public WeChatMsg(){

    }
    public WeChatMsg(UserMsg userMsg) {
        this.id = String.valueOf(userMsg.getId());
        this.sendId = userMsg.getSendId();
        this.acceptId = userMsg.getAcceptId();
        this.msg = userMsg.getMsg();
        this.isSign = userMsg.getIsSign();
        this.type = TypeChecksUtils.returnType(userMsg.getMsg());

        this.date = pacDTOs.pacTimeString(userMsg.getCreateTime());
    }
    public WeChatMsg(DataContent dataContent,Integer isSign) {
        this.type = dataContent.getExtand();
        if(dataContent.getChatMsg()!=null){
            this.id = dataContent.getChatMsg().getMsgId();
            this.sendId = Integer.parseInt(dataContent.getChatMsg().getSenderId());
            this.acceptId = Integer.parseInt(dataContent.getChatMsg().getReceiverId());
            this.msg = dataContent.getChatMsg().getMsg();
            this.isSign = isSign;
            this.date = pacDTOs.pacTimeString(new Date());
        }

    }
}
