package com.IM.netty.cache.threadLocal;

import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.utils.pacDTOs;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//todo 这里需要后期测试
public class UserInfoDTOLocalCache {
    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;

//    static ThreadLocal<List<UserInfoDTO>> userInfoDTOThreadLocal = new ThreadLocal<>();
    static ConcurrentHashMap<Integer,List<UserInfoDTO>> userInfoDTOThreadLocal = new ConcurrentHashMap<>();
    private UserInfoDTOLocalCache(){
    }

    //这里用在第一次初始化
    public static void set(Integer id,List<UserInfoDTO> user){
        userInfoDTOThreadLocal.put(id,user);
    }

    //这里根据success和fail的数值进行是否需要重新从数据库获取
    public static Integer setNewMessage(Integer id,Integer fid,String msg){
        List<UserInfoDTO> oldUserInfos=get(id);
        //删除旧的对应id的userInfo
        Optional<UserInfoDTO> userInfoDTO = Optional.ofNullable(oldUserInfos.stream().filter(oldUserInfo -> oldUserInfo.getId().equals(fid)).findAny().orElse(null));
        if(userInfoDTO.isPresent()) {
            oldUserInfos.removeIf(old -> old.getId().equals(fid));
            userInfoDTO.get().setLastChatWords(pacDTOs.pacMsg(msg));
            Date date = new Date();
            userInfoDTO.get().setLastChatTime(date);
            Optional<String> dateString = Optional.ofNullable(pacDTOs.pacTimeString(date));
            dateString.ifPresent(s -> userInfoDTO.get().setLastChatTimePac(s));
            oldUserInfos.add(userInfoDTO.get());
            //删除原本的copy
            remove(id);
            //排序
            set(id,pacDTOs.sortByCreateTime(oldUserInfos));
            return SUCCESS;
        }

        return FAIL;
    }

    public static List<UserInfoDTO>  get(Integer id){
        return userInfoDTOThreadLocal.get(id);
    }

    public static void remove(Integer id){
        userInfoDTOThreadLocal.remove(id);
    }
}
