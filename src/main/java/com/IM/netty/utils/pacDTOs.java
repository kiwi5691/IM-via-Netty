package com.IM.netty.utils;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class pacDTOs {
    public static List<UserInfoDTO> pacListUserInfoDTO(List<UserInfoDTO> userInfoDTOS){
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
    public static String pacTimeString(Date date){
        return DateTimeUtil.getDateTimeDisplayString(DateTimeUtil.DateToLocalDateTime(date),DateTimeUtil.WORDSWITHPM_AM);
    }
    public static String pacMsg(String msg){
        if(msg.length()>=10){
        StringBuilder builder = new StringBuilder();
        builder.append(msg.substring(0,9));
        builder.append("...");
        return builder.toString();
        }else {
            return msg;
        }
    }

    public static List<UserInfoDTO> sortByCreateTime(List<UserInfoDTO> list){
        //根据时间从小到大排序
        return list.stream().sorted(Comparator.comparing(UserInfoDTO::getLastChatTime).reversed()).collect(Collectors.toList());
    }

}
