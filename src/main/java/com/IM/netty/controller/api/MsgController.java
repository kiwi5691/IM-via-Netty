package com.IM.netty.controller.api;

import com.IM.netty.cache.UserStatusCacheMap;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import com.IM.netty.model.dto.ChatMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.UserInfoDTO;
import com.IM.netty.service.UserGroupsService;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.utils.JacksonUtil;
import com.IM.netty.utils.ResponseCode;
import com.IM.netty.utils.ResponseUtil;
import com.IM.netty.utils.pacDTOs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/wx")
public class MsgController {
    @Autowired
    private UserMsgService userMsgService;
    @Autowired
    private UserGroupsService userGroupsService;
    @PostMapping("/getUnReadMsgList")
    public Object getUnReadMsgList(String acceptUserId) {
        // 0. userId 判断不能为空
        if (StringUtils.isBlank(acceptUserId)) {
            return ResponseUtil.fail();
        }

        // 查询列表
//        List<ChatMsg> unreadMsgList = userService.getUnReadMsgList(acceptUserId);

        Map<String,Object> result = new HashMap<>();
        return ResponseUtil.ok(result);
    }
    @PostMapping("/getUserList")
    public Object getGroupList(@RequestBody String body) {
        Integer userId = JacksonUtil.parseInteger(body, "userId");
        Optional<List<UserGroups>> userGroups = Optional.ofNullable(userGroupsService.finAllFriends(userId));
        //构造response
        Map<String, Object> response = new HashMap<>();
        if (userGroups.isPresent()) {
            //获取所有好友
            Optional<Set<User>> users = Optional.ofNullable(userGroupsService.getFriendDetails(userGroups.get()));
            //获取构造userDTOList
            List<UserDTO> userDTOList = userMsgService.getUserMsgDTO(users.get(),userId,users);
            Optional<List<UserInfoDTO>> userInfoDTOS = Optional.ofNullable(userMsgService.listUserInfo(userDTOList));

            if(userInfoDTOS.isPresent()){
                response.put("groupList", pacDTOs.pacListUserInfoDTO(userInfoDTOS.get()));
                //有好友的情况下
                return ResponseUtil.ok(response);
            }
            //没私信的情况下
            return ResponseUtil.fail(ResponseCode.ZeroFirends, "您还没有私信");
        }
        return ResponseUtil.fail(ResponseCode.ZeroFirends, "您还没有私信");
    }
}
