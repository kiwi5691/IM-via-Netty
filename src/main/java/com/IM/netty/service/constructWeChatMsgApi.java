package com.IM.netty.service;

import com.IM.netty.cache.apiLocalCache.UserMsgLocalCache;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.WeChatMsg;
import com.IM.netty.utils.DtoUtils;
import com.IM.netty.utils.SpringUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class constructWeChatMsgApi {


    /*
     * 这里是一个辅助测试函数
     * 1.测试页面构造的时候没有创建UserMsgLocalCache值，所以这里创建
     * 2.微信Api双端测试的时候需要这个值来赋值
     */
    public static void constructWeChatMsgApi(Integer userId, Integer fid){
        UserMsgService userMsgService = (UserMsgService) SpringUtil.getBean("userMsgService");
        Optional<List<WeChatMsg>> userMsgs =  Optional.ofNullable(UserMsgLocalCache.get(userId,fid));
        if(!userMsgs.isPresent()){
            Optional<List<UserMsg>> userMsgs1 = Optional.ofNullable(userMsgService.listUserMsgByFid(userId, fid));
            if(userMsgs1.isPresent()){
                List<WeChatMsg> weChatMsgs = DtoUtils.copyUseMsg(userMsgs1);
                UserMsgLocalCache.set(userId,fid,weChatMsgs);
            }
        }
    }

    //同上，这里是无fid参数构造的，从所有好友中遍历
    public static void constructWeChatMsgApi(Integer userId, Optional<Set<User>> optionalUsers){
        UserMsgService userMsgService = (UserMsgService) SpringUtil.getBean("userMsgService");

        if(optionalUsers.isPresent()){
            List<Integer> fids = optionalUsers.get().stream().map(User::getId).collect(Collectors.toList());
            for (Integer fid: fids) {
                Optional<List<WeChatMsg>> userMsgs =  Optional.ofNullable(UserMsgLocalCache.get(userId,fid));
                if(!userMsgs.isPresent()){
                    Optional<List<UserMsg>> userMsgs1 = Optional.ofNullable(userMsgService.listUserMsgByFid(userId, fid));
                    if(userMsgs1.isPresent()){
                        List<WeChatMsg> weChatMsgs = DtoUtils.copyUseMsg(userMsgs1);
                        UserMsgLocalCache.set(userId,fid,weChatMsgs);
                    }
                }

            }
        }

    }
}
