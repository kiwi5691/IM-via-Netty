package com.IM.netty.cache;

import com.IM.netty.entity.User;
import io.netty.channel.Channel;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserStatusCacheMap {
    // 好友列表 ID，用户
    private final static Map<Integer,Set<User>> friendLists = new ConcurrentHashMap<>();

    private final static Map<String,Channel> channelByTokenMap = new ConcurrentHashMap<>();

    private final static Map<String, String> addressByTokenMap = new ConcurrentHashMap<>();

    public static void saveFriendLists(Integer id,Set<User> users ){
        friendLists.put(id,users);
    }
    public static Set<User> getFriendLists(Integer id){
        if (!friendLists.containsKey(id)){
            return null;
        }
        return friendLists.get(id);
    }




}
