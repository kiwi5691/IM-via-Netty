package com.IM.netty.cache.apiLocalCache;

import com.IM.netty.entity.UserMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class IsSignThreadLocal {
    private static ThreadLocal<List<UserMsg>> checkIsSign = new ThreadLocal<>();

    public static Boolean checkIsRemain(Integer userId){
        if(get()!=null){
            List<UserMsg> userMsgs = get();
            userMsgs.removeIf(e->e.getSendId().equals(userId));
            for(UserMsg userMsg:userMsgs){
                if(userMsg.getIsSign()!=1)
                    return true;
            }
            return false;
        }
        return false;
    }

    public static List<UserMsg> get(){
        if(checkIsSign.get()!=null){
            return checkIsSign.get();
        }else {
            return null;
        }
    }
    public static void set(List<UserMsg> userMsgList){
        if(!CollectionUtils.isEmpty(userMsgList)){
            checkIsSign.set(userMsgList);
        }
    }
}
