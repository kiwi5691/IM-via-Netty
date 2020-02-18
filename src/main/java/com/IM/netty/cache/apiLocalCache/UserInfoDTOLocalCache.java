package com.IM.netty.cache.apiLocalCache;

import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.utils.pacDTOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @Description: 这里是消息全局缓存，可以取消注释来开启
 */
public class UserInfoDTOLocalCache {
    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;

    static ConcurrentHashMap<Integer,List<UserInfoDTO>> userInfoDTOThreadLocal = new ConcurrentHashMap<>();
    private UserInfoDTOLocalCache(){
    }

    //这里用在第一次初始化
    public static void set(Integer id,List<UserInfoDTO> user){
        userInfoDTOThreadLocal.put(id,user);
    }

    //这里根据success和fail的数值进行是否需要重新从数据库获取
    public static void setNewMessage(Integer id,Integer fid,String msg){
        List<UserInfoDTO> oldUserInfos=get(id);
        if(oldUserInfos!=null) {

            //删除旧的对应id的userInfo
            UserInfoDTO userInfoDTO = oldUserInfos.stream().filter(oldUserInfo -> oldUserInfo.getId().equals(fid)).findAny().orElse(null);
            if (userInfoDTO != null) {
                oldUserInfos.removeIf(old -> old.getId().equals(fid));
                userInfoDTO.setLastChatWords(pacDTOs.pacMsg(msg));
                Date date = new Date();
                userInfoDTO.setLastChatTime(date);
                Optional<String> dateString = Optional.ofNullable(pacDTOs.pacTimeString(date));
                dateString.ifPresent(userInfoDTO::setLastChatTimePac);
                oldUserInfos.add(userInfoDTO);
                //删除原本的copy
                remove(id);
                //排序
                set(id, pacDTOs.sortByCreateTime(oldUserInfos));
            } else {
                UserInfoDTO userInfoDTO1 = new UserInfoDTO();
                userInfoDTO1.setLastChatWords(pacDTOs.pacMsg(msg));
                Date date = new Date();
                userInfoDTO1.setLastChatTime(date);
                Optional<String> dateString = Optional.ofNullable(pacDTOs.pacTimeString(date));
                dateString.ifPresent(userInfoDTO1::setLastChatTimePac);
                oldUserInfos.add(userInfoDTO1);
                set(id, pacDTOs.sortByCreateTime(oldUserInfos));
            }
        }else {
            UserInfoDTO userInfoDTO1 = new UserInfoDTO();
            userInfoDTO1.setLastChatWords(pacDTOs.pacMsg(msg));
            Date date = new Date();
            userInfoDTO1.setLastChatTime(date);
            Optional<String> dateString = Optional.ofNullable(pacDTOs.pacTimeString(date));
            dateString.ifPresent(userInfoDTO1::setLastChatTimePac);
            oldUserInfos.add(userInfoDTO1);
            set(id, pacDTOs.sortByCreateTime(oldUserInfos));
        }
    }

    public static List<UserInfoDTO>  get(Integer id){
        return userInfoDTOThreadLocal.get(id);
    }

    public static void remove(Integer id){
        userInfoDTOThreadLocal.remove(id);
    }
}
