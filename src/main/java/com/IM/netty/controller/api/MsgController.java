package com.IM.netty.controller.api;

import com.IM.netty.annotation.SecurityVerify;
import com.IM.netty.cache.apiLocalCache.IsSignThreadLocal;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.model.dto.WeChatMsg;
import com.IM.netty.service.UserGroupsService;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("msg/wx")
public class MsgController {
    @Autowired
    private UserMsgService userMsgService;
    @Autowired
    private UserGroupsService userGroupsService;

    @SecurityVerify
    @PostMapping("/getUnReadMsgList")
    public Object getUnReadMsgList(String acceptUserId) {
        // 0. userId 判断不能为空
        if (StringUtils.isBlank(acceptUserId)) {
            return ResponseUtil.fail();
        }

        Map<String,Object> result = new HashMap<>();
        return ResponseUtil.ok(result);
    }

    @SecurityVerify
    @PostMapping("/getUserList")
    public Object getGroupList(@RequestBody String body) {
        Map<String, Object> response = new HashMap<>();
        Integer userId = JacksonUtil.parseInteger(body, "userId");
        /* 缓存关闭
        if(UserInfoDTOLocalCache.get(userId)!=null){

            response.put("groupList",UserInfoDTOLocalCache.get(userId) );
            //有好友的情况下
            return ResponseUtil.ok(response);
        }else {
        */
            Optional<List<UserGroups>> userGroups = Optional.ofNullable(userGroupsService.finAllFriends(userId));
            //构造response
            if (userGroups.isPresent()) {
                //获取所有好友
                Optional<Set<User>> users = Optional.ofNullable(userGroupsService.getFriendDetails(userGroups.get()));
                //获取构造userDTOList
                List<UserDTO> userDTOList = userMsgService.getUserMsgDTO(users.get(), userId, users);
                Optional<List<UserInfoDTO>> userInfoDTOS = Optional.ofNullable(userMsgService.listUserInfo(userDTOList));
                if (userInfoDTOS.isPresent()) {
                    /*这里走concurrentHashMap存储
                    UserInfoDTOLocalCache.set(userId,pacDTOs.sortByCreateTime(pacDTOs.pacListUserInfoDTO(userInfoDTOS.get())));
                     */
                    response.put("groupList",pacDTOs.sortByCreateTime(pacDTOs.pacListUserInfoDTO(userInfoDTOS.get())));
                    //小程序端是否提醒 true/false
                    response.put("isRemain", IsSignThreadLocal.checkIsRemain(userId));
                    //有好友的情况下
                    return ResponseUtil.ok(response);
                }
                //没私信的情况下
                return ResponseUtil.fail(ResponseCode.ZEROFIRENDS.getCode(), ResponseCode.ZEROFIRENDS.getMessage());
            }
//        }
        return ResponseUtil.fail(ResponseCode.ZEROFIRENDS.getCode(), ResponseCode.ZEROFIRENDS.getMessage());
    }

    @SecurityVerify
    @PostMapping("/getFriendMsgs")
    public Object getFriendMsgs(@RequestBody String body){
        Integer userId = JacksonUtil.parseInteger(body, "userId");
        Integer fid = JacksonUtil.parseInteger(body, "fid");
        Map<String,Object> result = new HashMap<>();
/*缓存关闭
        Optional<List<WeChatMsg>> userMsgs =  Optional.ofNullable(UserMsgLocalCache.get(userId,fid));
        if(userMsgs.isPresent()){
            result.put("userMsgs",userMsgs.get());
            return ResponseUtil.ok(result);
        }else {
 */
            Optional<List<UserMsg>> userMsgs1 = Optional.ofNullable(userMsgService.listUserMsgByFid(userId, fid));
            if(userMsgs1.isPresent()){
                List<WeChatMsg> weChatMsgs = DtoUtils.copyUseMsg(userMsgs1);
               /*
                UserMsgLocalCache.set(userId,fid,weChatMsgs);
                */
                result.put("userMsgs",weChatMsgs);
                return ResponseUtil.ok(result);
            }else {

                return ResponseUtil.fail(ResponseCode.ZEROMSGS.getCode(), ResponseCode.ZEROMSGS.getMessage());
            }

    }

    @SecurityVerify
    @PostMapping("/selfUnReadMsg")
    public Object selfUnReadMsg(@RequestBody String body){
        Integer userId = JacksonUtil.parseInteger(body, "userId");
        Integer fid = JacksonUtil.parseInteger(body, "fid");
        Map<String,Object> result = new HashMap<>();
        Optional<List<UserMsg>> userMsgs1 = Optional.ofNullable(userMsgService.selfUnReadMsg(fid, userId));
        if(userMsgs1.isPresent()){
            List<WeChatMsg> weChatMsgs = DtoUtils.copyUseMsg(userMsgs1);
            result.put("userMsgs",weChatMsgs);
            return ResponseUtil.ok(result);
        }
        return  ResponseUtil.fail();
    }
}
