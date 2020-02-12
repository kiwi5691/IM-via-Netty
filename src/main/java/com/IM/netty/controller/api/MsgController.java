package com.IM.netty.controller.api;

import com.IM.netty.model.dto.ChatMsg;
import com.IM.netty.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class MsgController {
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
}
