package com.IM.netty.model.dto;


import com.IM.netty.entity.UserMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserInfoDTO {
    private Integer id;
    private String nickname;
    private String avatar;
    private Date lastChatTime;
    //封装lastChatTime
    private String lastChatTimePac;
    private String lastChatWords;
}
