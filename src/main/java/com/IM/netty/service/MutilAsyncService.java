package com.IM.netty.service;

import com.IM.netty.entity.UserMsg;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class MutilAsyncService {
    private Set<UserMsg> userMsgsAsyncSets = new LinkedHashSet<>();

    public void save(Object user,Object msg){
        UserMsg userMsg = new UserMsg();
        userMsg.setName(String.valueOf(user));
        userMsg.setMsg(String.valueOf(msg));
        userMsgsAsyncSets.add(userMsg);
    }

    public Set<UserMsg> cloneCacheMap(){
        return userMsgsAsyncSets;
    }

    public void clearCacheMap(){
        userMsgsAsyncSets.clear();
    }
}
