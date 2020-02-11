package com.IM.netty.model.dto;

import com.IM.netty.entity.UserMsg;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ToString
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private Byte gender;
    private Date lastLoginTime;
    private String lastLoginIp;
    private String nickname;
    private String mobile;
    private String avatar;
    @DateTimeFormat(pattern="hh:mm")
    private Date lastChatTime;
    private List<UserMsg> userMsgs;
}
