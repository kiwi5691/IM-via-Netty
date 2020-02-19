package com.IM.netty.cache;

import com.IM.netty.entity.JwtToken;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.model.dto.WeChatMsg;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtLocalCache {
    static ConcurrentHashMap<Integer, String> jwtConcurrentHashMap = new ConcurrentHashMap<>();

    public JwtLocalCache(){
    }

    public static void JwtLocalCacheUpdate(Iterable<JwtToken> iterable){
        iterable.forEach(e->set(e.getId(),e.getToken()));
    }

    public static void set(Integer id,String tokens){
        jwtConcurrentHashMap.put(id,tokens);
    }

    public static String  get(Integer id){
        return jwtConcurrentHashMap.get(id);
    }

}