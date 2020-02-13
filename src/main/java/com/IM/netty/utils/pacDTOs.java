package com.IM.netty.utils;

import com.IM.netty.model.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class pacDTOs {
    public static List<UserInfoDTO> pacListUserInfoDTO(List<UserInfoDTO> userInfoDTOS){
        List<UserInfoDTO> userInfoDTO = new ArrayList<>();
        userInfoDTOS.forEach(userInfoDTO1 -> {
            if(userInfoDTO1.getLastChatWords().length()>=10){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(userInfoDTO1.getLastChatWords().substring(0,9));
                stringBuilder.append("...");
                userInfoDTO1.setLastChatWords(stringBuilder.toString());
            }
            String dateTimeDisplayString=DateTimeUtil.getDateTimeDisplayString(DateTimeUtil.DateToLocalDateTime(userInfoDTO1.getLastChatTime()),DateTimeUtil.WORDSWITHPM_AM);
            if(dateTimeDisplayString!=null){
                userInfoDTO1.setLastChatTimePac(dateTimeDisplayString);
            }
        });
        return userInfoDTOS;
    }
}
