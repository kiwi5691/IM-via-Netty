package com.IM.netty.controller.view;

import com.IM.netty.cache.UserStatusCacheMap;
import com.IM.netty.entity.User;
import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.UserDTO;
import com.IM.netty.model.dto.WeChatMsg;
import com.IM.netty.service.UserGroupsService;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.service.UserService;
import com.IM.netty.service.constructWeChatMsgApi;
import com.IM.netty.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    @Autowired
    private UserMsgService userMsgService;
    @Autowired
    private HttpSession session;

    @GetMapping({"/friends","/friends.html"})
    public String getFriends(Model model) {
        Object id = session.getAttribute("currentId");
        if(id==null){
            model.addAttribute("hide",true);
            return "redirect:/login";
        }
        Optional<Set<User>> optionalUsers = Optional.ofNullable(UserStatusCacheMap.getFriendLists(Integer.parseInt(id.toString())));
        if(optionalUsers.isPresent()) {
            Map<String,Object> map = getFirstOne(optionalUsers.get());
            model.addAttribute("users", optionalUsers.get());
            model.addAttribute("fNickName", map.get("fNickName"));
            model.addAttribute("fId", map.get("fId"));
            return "friendList";
        }else {
            model.addAttribute("hide",true);
            return "redirect:/login";
        }

    }

    @GetMapping("/toChatDetail/{fid}")
    public String toChatDetail(@PathVariable Integer fid,Model model){
        Object id = session.getAttribute("currentId");
        Optional<Set<User>> optionalUsers = Optional.ofNullable(UserStatusCacheMap.getFriendLists(Integer.parseInt(id.toString())));
        if(optionalUsers.isPresent()) {
            Map<String,Object> map = getFirstOne(optionalUsers.get());
            List<UserDTO> userDTOList = userMsgService.getUserMsgDTO(optionalUsers.get(),Integer.parseInt(id.toString()),optionalUsers);
            //构造api存储
            constructWeChatMsgApi.constructWeChatMsgApi(Integer.parseInt(id.toString()),fid);
            model.addAttribute("users", userDTOList);

            model.addAttribute("fId", fid);
            User user= optionalUsers.get().stream().filter(o -> o.getId().equals(fid)).findAny().orElse(null);
            model.addAttribute("fNickName", user.getNickname());

        }else {
            model.addAttribute("users", null);
            model.addAttribute("fId", null);
            model.addAttribute("fNickName", null);

        }
        return "chatting";
    }
    @GetMapping({"/chatting","/chatting.html"})
    public String getChatting(Model model) {
        Object id = session.getAttribute("currentId");
        if(id==null){
            model.addAttribute("hide",true);
            return "redirect:/login";
        }
        Optional<Set<User>> optionalUsers = Optional.ofNullable(UserStatusCacheMap.getFriendLists(Integer.parseInt(id.toString())));
        if(optionalUsers.isPresent()) {
            Map<String,Object> map = getFirstOne(optionalUsers.get());
            List<UserDTO> userDTOList = userMsgService.getUserMsgDTO(optionalUsers.get(),Integer.parseInt(id.toString()),optionalUsers);
            constructWeChatMsgApi.constructWeChatMsgApi(Integer.parseInt(id.toString()),optionalUsers);
            model.addAttribute("users", userDTOList);
            model.addAttribute("fNickName", map.get("fNickName"));
            model.addAttribute("fId", map.get("fId"));

            return "chatting";
        }

        return "redirect:/login";
    }

    public Map<String,Object> getFirstOne(Set<User> userSet){
        Map<String,Object> map = new HashMap<>();
        map.put("fId",userSet.iterator().next().getId());
        map.put("fNickName",userSet.iterator().next().getNickname());
        return map;
    }


}
