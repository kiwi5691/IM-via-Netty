package com.IM.netty.cache.apiLocalCache;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.enums.MsgSignFlagEnum;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.model.dto.WeChatMsg;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 *
 * @Description: 这里是消息详细缓存，可以取消注释来开启
 */
public class UserMsgLocalCache {
    //userId,Map ->fid,UserMsg
    static ConcurrentHashMap<Integer,Map<Integer, List<WeChatMsg>>> userMapConcurrentHashMap = new ConcurrentHashMap<>();

    public static void set(Integer userId,Integer fid,List<WeChatMsg> userMsgList){
        Map<Integer,List<WeChatMsg>> integerListMap = new HashMap<>();
        integerListMap.put(fid,userMsgList);
        userMapConcurrentHashMap.put(userId,integerListMap);
    }
    public static void add(Integer userId,Integer fid,WeChatMsg weChatMsg){
        Map<Integer,List<WeChatMsg>> integerListMap = new HashMap<>();
        List<WeChatMsg> weChatMsgs = get(userId,fid);
        if(weChatMsgs!=null) {
            weChatMsgs.add(weChatMsg);
            integerListMap.put(fid, weChatMsgs);
            userMapConcurrentHashMap.put(userId, integerListMap);
        }else {
            weChatMsgs.add(weChatMsg);
            integerListMap.put(fid, weChatMsgs);
            userMapConcurrentHashMap.put(userId, integerListMap);

        }
    }

    public static List<WeChatMsg> get(Integer userId,Integer fid){
        Optional<Map<Integer,List<WeChatMsg>>> integerListMap = Optional.ofNullable(userMapConcurrentHashMap.get(userId));
        return integerListMap.map(listMap -> listMap.get(fid)).orElse(null);
    }

    public static void remove(Integer userId,Integer fid){
        //删除所有userId的Msg;
        userMapConcurrentHashMap.remove(userId);
    }

    //更新是否已读
    public static void updateSign(List<String> msgIdList){
        for(String id:msgIdList){
            for (Map.Entry<Integer,Map<Integer, List<WeChatMsg>>> entry : userMapConcurrentHashMap.entrySet()) {
                //多层for循环嵌套并不规范，并且耗时，所以这里使用并行流运算，这里只是针对id的已/未读进行修改，不会出现非线程安全
                List<WeChatMsg> weChatMsgsPraplleSets = new ArrayList<>();
                entry.getValue().entrySet().parallelStream().forEach(integerListEntry -> integerListEntry.getValue().forEach(
                            weChatMsg -> {
                                if (weChatMsg.getId().equals(Long.parseLong(id))) {
                                    weChatMsg.setIsSign(MsgSignFlagEnum.unsign.type);
                                }
                            })
                );
            }
        }
    }



}
