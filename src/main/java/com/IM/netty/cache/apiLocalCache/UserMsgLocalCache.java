package com.IM.netty.cache.apiLocalCache;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.model.dto.WeChatMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMsgLocalCache {
    //userId,Map ->fid,UserMsg
    static ConcurrentHashMap<Integer,Map<Integer, List<WeChatMsg>>> userMapConcurrentHashMap = new ConcurrentHashMap<>();

    public static void set(Integer userId,Integer fid,List<WeChatMsg> userMsgList){
        Map<Integer,List<WeChatMsg>> integerListMap = new HashMap<>();
        integerListMap.put(fid,userMsgList);
        userMapConcurrentHashMap.put(userId,integerListMap);
    }

    public static List<WeChatMsg> get(Integer userId,Integer fid){
        Optional<Map<Integer,List<WeChatMsg>>> integerListMap = Optional.ofNullable(userMapConcurrentHashMap.get(userId));
        return integerListMap.map(listMap -> listMap.get(fid)).orElse(null);
    }
    public static void remove(Integer userId,Integer fid){
        //删除所有userId的Msg;
        userMapConcurrentHashMap.remove(userId);
    }



}
